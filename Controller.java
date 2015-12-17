package sample;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class Controller {
    @FXML public HBox mainHbox = new HBox();
    @FXML public StackPane mainContainer = new StackPane();

    @FXML
    public void initialize() throws IOException {
        //The errror box
        RudeErrorPane errorPane = new RudeErrorPane();
        JFXDialog dialog =  new JFXDialog(mainContainer, errorPane, JFXDialog.DialogTransition.CENTER);
        errorPane.button.setOnMouseClicked((_e) -> dialog.close());

        MainListCard mainListCard = new MainListCard(dialog);
        mainHbox.getChildren().add(mainListCard);
        //Profile card instantiation
        ProfileCardReusable profileCard = new ProfileCardReusable();
        mainHbox.getChildren().add(profileCard);


        mainListCard.myListView.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (mainListCard.listViewData.contains(newVal)) {
                profileCard.setMainPerson(newVal);
            }
        });

        //Fire these events so it all looks right to the user
        mainListCard.titleCell.subMenu.getSelectionModel().select(0);
        mainListCard.titleCell.mainText.setActive(true);
        mainListCard.myListView.getSelectionModel().select(0);
    }
}
