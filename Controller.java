package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.effects.JFXDepthManager;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Stack;

public class Controller {
    @FXML public JFXListView<RudeCell> myListView = new JFXListView();
    @FXML public HBox mainContainer = new HBox();
    @FXML public StackPane stackPane = new StackPane();
    @FXML public JFXButton actionButton = new JFXButton();
    @FXML public JFXComboBox<String> testComboBox = new JFXComboBox<>();
    private ObservableList<RudeCell> listViewData = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws IOException {
        myListView.setCellFactory((list) -> {
            JFXListCell cell = new RudeCellCell();
            return cell;
        });

        myListView.getSelectionModel().selectedItemProperty().addListener((o, oldVal, cellToDelete) -> {
            //There is a chance it still has a deleted element selected
            if (listViewData.contains(cellToDelete)) {
                cellToDelete.toDeleteProperty().addListener((_o, _oldVal, newVal) -> {
                    if (newVal) {
                        int index = listViewData.indexOf(cellToDelete);
                        myListView.getSelectionModel().select(oldVal);
                        TranslateTransition deleteAnimation = new TranslateTransition(Duration.millis(700), cellToDelete);
                        deleteAnimation.setByX(cellToDelete.getWidth() * 1.2);
                        deleteAnimation.setCycleCount(1);
                        deleteAnimation.setInterpolator(Animations.materialInterp);
                        deleteAnimation.play();

                        PauseTransition pauseTransition = new PauseTransition(Duration.millis(200));
                        pauseTransition.setOnFinished((e) -> {
                            ParallelTransition parallelTransition = new ParallelTransition();
                            for (int i = index; i < listViewData.size(); i++) {
                                TranslateTransition replaceAnimation = new TranslateTransition(Duration.millis(700), listViewData.get(i));
                                replaceAnimation.setToY(cellToDelete.getHeight() * -1.8); //2.0 overshoots just a hair
                                replaceAnimation.setCycleCount(1);
                                replaceAnimation.setInterpolator(Animations.materialInterp);
                                parallelTransition.getChildren().add(replaceAnimation);
                            }
                            parallelTransition.play();
                            PauseTransition _pauseTransition = new PauseTransition(Duration.millis(700));
                            _pauseTransition.setOnFinished((_e) -> {
                                ParallelTransition _parallelTransition = new ParallelTransition();
                                for (int i = index; i < listViewData.size(); i++) {
                                    TranslateTransition replaceAnimation = new TranslateTransition(Duration.ONE, listViewData.get(i));
                                    replaceAnimation.setToY(0);
                                    replaceAnimation.setCycleCount(1);
                                    replaceAnimation.setInterpolator(Animations.materialInterp);
                                    _parallelTransition.getChildren().add(replaceAnimation);
                                }
                                _parallelTransition.play();
                                PauseTransition __pauseTransition = new PauseTransition(Duration.ONE);
                                __pauseTransition.setOnFinished((__e) -> listViewData.remove(cellToDelete));
                                __pauseTransition.play();
                            });
                            _pauseTransition.play();
                        });
                        pauseTransition.play();
                    }
                });
            }
        });
        myListView.getStyleClass().add("mylistview");

        TitleCell l = new TitleCell("Name");
        l.activeButtonProperty().addListener((o, oldVal, newVal) -> {
            sort(newVal, newVal.getSortAscending());
            newVal.sortAscendingProperty().addListener((_o, _oldVal, _newVal) -> {
                sort(newVal, _newVal);
            });

            newVal.sortPropertyProperty().addListener((_o, _oldVal, _newVal) -> {
                sort(newVal, newVal.getSortAscending());
            });
        });

        testComboBox.getItems().addAll("Test", "1", "2");

        l.subMenuButton.sortPropertyProperty().addListener((o, oldVal, newVal) -> {
            for (RudeCell cell: listViewData) {
                if (cell instanceof Person) {
                    Person person = (Person) cell;
                    person.setSubLabelProperty(newVal);
                }
            }
        });
        listViewData.add(l);
        for (int i = 0; i < 16; i++) {
            listViewData.add(RandomPersonFactory.randomPerson());
        }

        myListView.setItems(listViewData);
        listViewData.sort(new RudeCellComparator(l.mainText.getSortProperty()));
        //The overview card is setup now to setup the Profile Card

        URL location = getClass().getResource("profileCard.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent profileCard = (Parent) fxmlLoader.load(location.openStream());
        mainContainer.setMargin(profileCard, new Insets(10, 5, 5, 10));
        mainContainer.getChildren().add(profileCard);

        ProfileCardController profileCardController = fxmlLoader.getController();

        myListView.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
           if (newVal instanceof Person && listViewData.contains(newVal)) {
               profileCardController.setMainPerson((Person) newVal);
            }
        });

        //Fire these events so it all looks right to the user
        l.subMenu.getSelectionModel().select(0);
        l.mainText.setActive(true);
        myListView.getSelectionModel().select(1);

        actionButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            Animations.FloatingActionButtonPressed.setNode(actionButton);
            Animations.FloatingActionButtonPressed.play();
        });

        actionButton.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
            if (!actionButton.contains(e.getX(), e.getY())) {
                Animations.FloatingActionButtonReleased.setNode(actionButton);
                Animations.FloatingActionButtonReleased.play();
            }
        });

        actionButton.setOnMouseClicked((e) -> {
            FXMLLoader _fxmlLoader = new FXMLLoader();
            _fxmlLoader.setLocation(location);
            _fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent inputProfileCard = null;
            try {
                inputProfileCard = (Parent) _fxmlLoader.load(location.openStream());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mainContainer.setMargin(inputProfileCard, new Insets(10, 5, 5, 10));
            mainContainer.getChildren().add(inputProfileCard);

            ProfileCardController inputProfileCardController = _fxmlLoader.getController();

            inputProfileCard.setScaleX(0);
            inputProfileCard.setScaleY(0);
            inputProfileCard.setTranslateX(180);
            inputProfileCard.setTranslateY(180);

            Animations.FloatingActionButtonClicked.setNode(actionButton);
            Animations.FloatingActionButtonClicked.play();

            Animations.NewCard.setNode(inputProfileCard);
            Animations.NewCard.play();

            int index = stackPane.getChildren().indexOf(actionButton);
            JFXDepthManager.setDepth(inputProfileCard, 4);
            stackPane.getChildren().add(index, inputProfileCard);
        });
    }

    private void sort(RudeSortButton rudeSortButton, boolean forwards) {
        if (forwards) {
            listViewData.sort(new RudeCellComparator(rudeSortButton.getSortProperty()));
        } else {
            listViewData.sort(new RudeCellComparator(rudeSortButton.getSortProperty()).reversed());
        }
    }
}
