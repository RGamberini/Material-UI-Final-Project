package sample;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Controller {
    @FXML public JFXListView<RudeCell> myListView = new JFXListView();
    @FXML public VBox inputVBOX = new VBox();
    private ObservableList<RudeCell> listViewData = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws IOException {
        myListView.setCellFactory((list) -> {
            JFXListCell cell = new RudeCellCell();
            return cell;
        });

        myListView.setOnMouseMoved((e) -> {
            for (RudeCell rudeCell: myListView.getItems()) {
                rudeCell.handleHoverAction(e);
            }
        });
        myListView.getStyleClass().add("mylistview");

        listViewData.add(new RudeCell("Hans Muster"));
        listViewData.add(new RudeCell("Ruth Mueller"));
        listViewData.add(new RudeCell("Heinz Kurz"));
        listViewData.add(new RudeCell("Cornelia Meier"));
        listViewData.add(new RudeCell("Werner Meyer"));
        listViewData.add(new RudeCell("Lydia Kunz"));
        listViewData.add(new RudeCell("Anna Best"));
        listViewData.add(new RudeCell("Stefan Meier"));
        listViewData.add(new RudeCell("Martin Mueller"));
        myListView.setItems(listViewData);
        //myListView.getItems().add(rudeCell);
    }
}
