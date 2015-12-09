package sample;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Random;

public class Controller {
    @FXML public JFXListView<RudeCell> myListView = new JFXListView();
    @FXML public VBox inputVBOX = new VBox();
    private ObservableList<RudeCell> listViewData = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws IOException {
        Random rng = new Random();
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

        TitleCell l = new TitleCell("Name");
        l.sort_ascendingProperty().addListener((o, oldVal, newVal) -> {
            System.out.println("Shots fired");
            if (newVal) {
                listViewData.sort(new RudeCellComparator());
            } else {
                listViewData.sort(new RudeCellComparator().reversed());
            }
        });
        listViewData.add(l);

        listViewData.add(new Student("Hans", "Muster", (double) ((int) (rng.nextDouble() * 10) / 10)));
        listViewData.add(new Student("Ruth", "Mueller", (double) ((int) (rng.nextDouble() * 10) / 10)));
        listViewData.add(new Student("Heinz", "Kurz", (double) ((int) (rng.nextDouble() * 10) / 10)));
        listViewData.add(new Student("Cornelia", "Meier", (double) ((int) (rng.nextDouble() * 10) / 10)));
        listViewData.add(new Student("Werner", "Meyer", (double) ((int) (rng.nextDouble() * 10) / 10)));
        listViewData.add(new Student("Lydia", "Kunz", (double) ((int) (rng.nextDouble() * 10) / 10)));
        listViewData.add(new Student("Anna", "Best", (double) ((int) (rng.nextDouble() * 10) / 10)));
        listViewData.add(new Student("Stefan", "Meier", (double) ((int) (rng.nextDouble() * 10) / 10)));
        listViewData.add(new Student("Martin", "Mueller", (double) ((int) (rng.nextDouble() * 10) / 10)));

        myListView.setItems(listViewData);
        listViewData.sort(new RudeCellComparator());
        //myListView.getItems().add(rudeCell);
    }
}
