package sample;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Controller {
    @FXML public JFXListView<RudeCell> myListView = new JFXListView();
    @FXML public HBox mainContainer = new HBox();
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

        Parent root = (Parent) fxmlLoader.load(location.openStream());
        mainContainer.setMargin(root, new Insets(10, 5, 5, 10));
        mainContainer.getChildren().add(root);

        ProfileCardController profileCard = fxmlLoader.getController();
        //profileCard.setMainPerson((Person) listViewData.get(1));

        myListView.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
           if (newVal instanceof Person && listViewData.contains(newVal)) {
               profileCard.setMainPerson((Person) newVal);
            }
        });

        //Fire these events so it all looks right to the user
        l.subMenu.getSelectionModel().select(0);
        l.mainText.setActive(true);
        myListView.getSelectionModel().select(1);

    }

    private void sort(RudeSortButton rudeSortButton, boolean forwards) {
        if (forwards) {
            listViewData.sort(new RudeCellComparator(rudeSortButton.getSortProperty()));
        } else {
            listViewData.sort(new RudeCellComparator(rudeSortButton.getSortProperty()).reversed());
        }
    }
}
