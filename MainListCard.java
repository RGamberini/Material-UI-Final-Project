package sample;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by Nick on 12/16/2015.
 */
public class MainListCard  extends StackPane {
    public JFXListView<Person> myListView = new JFXListView();
    public JFXButton actionButtonAdd = new JFXButton();
    public JFXButton actionButtonSearch = new JFXButton();
    public JFXTabPane tabPane = new JFXTabPane();
    public VBox mainVbox = new VBox();
    public ObservableList<Person> listViewData = FXCollections.observableArrayList();
    public TitleCell titleCell;

    public MainListCard(JFXDialog dialog) throws IOException {
        super();
        this.setAlignment(Pos.BOTTOM_RIGHT);

        HBox.setMargin(this, new Insets(10, 5, 10, 16));

        mainVbox.setPrefSize(385, 385);
        mainVbox.getStyleClass().add("card");
        this.getChildren().add(mainVbox);

        myListView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(myListView, Priority.ALWAYS);
        mainVbox.getChildren().add(myListView);

        actionButtonAdd.setButtonType(JFXButton.ButtonType.RAISED);
        actionButtonAdd.setRipplerFill(Paint.valueOf("#757575"));
        actionButtonAdd.getStyleClass().add("floating-action-button");
        StackPane.setMargin(actionButtonAdd, new Insets(0, 0, -30, 0));
        MaterialDesignIconView add = new MaterialDesignIconView(MaterialDesignIcon.PLUS);
        add.setSize("32");
        add.setFill(Paint.valueOf("WHITE"));
        actionButtonAdd.setGraphic(add);
        this.getChildren().add(actionButtonAdd);

        actionButtonSearch.setButtonType(JFXButton.ButtonType.RAISED);
        actionButtonSearch.setRipplerFill(Paint.valueOf("#757575"));
        actionButtonSearch.getStyleClass().add("floating-action-button");
        StackPane.setMargin(actionButtonSearch, new Insets(0, 70, -30, 0));
        MaterialDesignIconView search = new MaterialDesignIconView(MaterialDesignIcon.MAGNIFY);
        search.setSize("32");
        search.setFill(Paint.valueOf("WHITE"));
        actionButtonSearch.setGraphic(search);
        this.getChildren().add(actionButtonSearch);
        initialize(dialog);
    }

    @FXML
    public void initialize(JFXDialog dialog) throws IOException {
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

        titleCell = new TitleCell("Name");
        titleCell.activeButtonProperty().addListener((o, oldVal, newVal) -> {
            sort(newVal, newVal.getSortAscending());
            newVal.sortAscendingProperty().addListener((_o, _oldVal, _newVal) -> {
                sort(newVal, _newVal);
            });

            newVal.sortPropertyProperty().addListener((_o, _oldVal, _newVal) -> {
                sort(newVal, newVal.getSortAscending());
            });
        });

        mainVbox.setSpacing(14);
        mainVbox.getChildren().add(0, titleCell);
        VBox.setMargin(titleCell, new Insets(4, 0, 0, 0));

        titleCell.subMenuButton.sortPropertyProperty().addListener((o, oldVal, newVal) -> {
            for (Person cell: listViewData) {
                cell.setSubLabelProperty(newVal);
            }
        });

//        listViewData.add(l);

        for (int i = 0; i < 16; i++) {
            listViewData.add(RandomPersonFactory.randomPerson());
        }

        myListView.setItems(listViewData);
        listViewData.sort(new RudeCellComparator(titleCell.mainText.getSortProperty()));
        //The overview card is setup now to setup the Profile Card

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

            ParallelTransition parallelTransition = new ParallelTransition();
            for (JFXButton _button: floatingActionButtons) {
                parallelTransition.getChildren().add(Animations.newFloatingActionButtonClickedAnimation(_button));
            }

            parallelTransition.getChildren().add(Animations.newCardAnimation(profileCardInput));
            parallelTransition.play();

            int index = this.getChildren().indexOf(myListView.getParent());
            JFXDepthManager.setDepth(profileCardInput, 4);
            this.getChildren().add(index + 1, profileCardInput);

            profileCardInput.setOnClear((clear) -> {
                ParallelTransition buttonRestore = new ParallelTransition();
                for (JFXButton _button : floatingActionButtons) {
                    buttonRestore.getChildren().add(Animations.newFloatingActionButtonRestoredAnimation(_button));
                }
                ParallelTransition cardDestroy = Animations.newCardDestroyAnimation(profileCardInput);
                //cardDestroy.setOnFinished((_e) -> buttonRestore.play());
                cardDestroy.play();
                buttonRestore.play();
            });

            profileCardInput.setOnError((error) -> {
                dialog.show();
            });

            profileCardInput.setOnAccept((accept) -> {
                Person personToBe = profileCardInput.getPersonToBe();
                personToBe.setSubLabelProperty(titleCell.subMenuButton.getSortProperty());
                listViewData.add(personToBe);
                sort(titleCell.activeButtonProperty().get(), titleCell.activeButtonProperty().get().getSortAscending());
            });
        });

        actionButtonSearch.setOnMouseClicked((e) -> {
            SearchCard searchCard = new SearchCard(listViewData);

            searchCard.setScaleX(0);
            searchCard.setScaleY(0);
            searchCard.setTranslateX(180);
            searchCard.setTranslateY(180);

            ParallelTransition parallelTransition = new ParallelTransition();
            for (JFXButton _button: floatingActionButtons) {
                parallelTransition.getChildren().add(Animations.newFloatingActionButtonClickedAnimation(_button));
            }

            parallelTransition.getChildren().add(Animations.newCardAnimation(searchCard));
            parallelTransition.play();

            int index = this.getChildren().indexOf(myListView.getParent());
            JFXDepthManager.setDepth(searchCard, 4);
            this.getChildren().add(index + 1, searchCard);
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
