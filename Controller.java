package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.effects.JFXDepthManager;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
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
        Random rng = new Random();
        myListView.setCellFactory((list) -> {
            JFXListCell cell = new RudeCellCell();
            return cell;
        });

        myListView.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            //There is a chance it still has a deleted element selected
            if (listViewData.contains(newVal)) {
                newVal.toDeleteProperty().addListener((_o, _oldVal, _newVal) -> {
                    if (_newVal) {
                        listViewData.remove(newVal);
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
        for (int i = 0; i < 9; i++) {
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
            ScaleTransition preButtonScale = new ScaleTransition(Duration.millis(100), actionButton);
            preButtonScale.setToX(1.2);
            preButtonScale.setToY(1.2);
            preButtonScale.setCycleCount(1);
            preButtonScale.setInterpolator(Interpolator.SPLINE(0, 0, 1, 1));
            preButtonScale.play();
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
            ScaleTransition cardScale = new ScaleTransition(Duration.millis(300), inputProfileCard);
            TranslateTransition cardTranslate = new TranslateTransition(Duration.millis(300), inputProfileCard);

            cardTranslate.setByX(-180);
            cardTranslate.setByY(-180);
            cardTranslate.setCycleCount(1);
            cardTranslate.setInterpolator(Interpolator.SPLINE(0, 0, 1, 1));

            cardScale.setInterpolator(Interpolator.SPLINE(0, 0, 1, 1));
            cardScale.setByX(.98);
            cardScale.setByY(.98);
            cardScale.setCycleCount(1);


            ScaleTransition buttonScale = new ScaleTransition(Duration.millis(200), actionButton);
            buttonScale.setToX(0);
            buttonScale.setToY(0);
            buttonScale.setCycleCount(1);
            buttonScale.setInterpolator(Interpolator.SPLINE(0, 0, 1, 1));
            buttonScale.play();

            ParallelTransition pt = new ParallelTransition();
            pt.getChildren().addAll(cardScale, cardTranslate);
            pt.play();
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
