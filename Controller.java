package sample;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class Controller {
    @FXML public JFXListView<RudeCell> myListView = new JFXListView();
    @FXML public HBox mainContainer = new HBox();
    @FXML public StackPane stackPane = new StackPane();
    @FXML public JFXButton actionButtonAdd = new JFXButton();
    @FXML public JFXButton actionButtonSearch = new JFXButton();
    @FXML public JFXComboBox<String> testComboBox = new JFXComboBox<>();
    @FXML public JFXTabPane tabPane = new JFXTabPane();
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

        //Profile card instantiation
        ProfileCardReusable profileCard = new ProfileCardReusable();
        mainContainer.getChildren().add(profileCard);


        myListView.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
           if (newVal instanceof Person && listViewData.contains(newVal)) {
               profileCard.setMainPerson((Person) newVal);
            }
        });

        //Fire these events so it all looks right to the user
        l.subMenu.getSelectionModel().select(0);
        l.mainText.setActive(true);
        myListView.getSelectionModel().select(1);

        JFXButton[] floatingActionButtons = new JFXButton[]{actionButtonSearch, actionButtonAdd};
        //Setup the event handlers
        for (JFXButton button: floatingActionButtons) {
            button.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                Animations.newFloatingActionButtonPressedAnimation(button).play();
            });

            button.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
                if (!button.contains(e.getX(), e.getY())) {
                    Animations.newFloatingActionButtonReleasedAnimation(button).play();
                }
            });
        }

        actionButtonAdd.setOnMouseClicked((e) -> {
            ProfileCardInput profileCardInput = new ProfileCardInput();

            profileCardInput.setScaleX(0);
            profileCardInput.setScaleY(0);
            profileCardInput.setTranslateX(180);
            profileCardInput.setTranslateY(180);

            for (JFXButton _button: floatingActionButtons) {
                Animations.newFloatingActionButtonClickedAnimation(_button).play();
            }

            Animations.newCardAnimation(profileCardInput).play();

            JFXDepthManager.setDepth(profileCardInput, 4);
            stackPane.getChildren().add(profileCardInput);

            for (JFXButton button: profileCardInput.buttons) {
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (_e) -> {
                    for (JFXButton _button: floatingActionButtons) {
                        Animations.newFloatingActionButtonRestoredAnimation(_button).play();
                    }
                });
            }
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
