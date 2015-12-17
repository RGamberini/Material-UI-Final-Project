package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by Nick on 12/16/2015.
 */
public class SearchCard extends VBox {
    private JFXComboBox<String> propertyList;
    private JFXTextField searchInput;
    private JFXListView searchResultView;
    private ObservableList<Person> searchResultData;
    private ObservableList<Person> fullSetData;
    private StringProperty searchProperty;

    public SearchCard(ObservableList<Person> fullSetData) {
        super();
        this.fullSetData = fullSetData;
        searchProperty = new SimpleStringProperty();

        HBox.setMargin(this, new Insets(10, 5, 5, 10));
        this.setPrefSize(385, 385);
        this.getStyleClass().add("card");

        HBox tempHbox = new HBox();
        this.getChildren().add(tempHbox);

        propertyList = new JFXComboBox<>();
        propertyList.getItems().addAll(Person.properties);
        HBox.setMargin(propertyList, new Insets(0, 15, 0, 0));

        searchInput = new JFXTextField();
        HBox.setHgrow(searchInput, Priority.ALWAYS);

        tempHbox.getChildren().addAll(propertyList, searchInput);

        searchResultView = new JFXListView();
        this.getChildren().add(searchResultView);
        VBox.setVgrow(searchResultView, Priority.ALWAYS);

        searchResultData = FXCollections.observableArrayList();
        searchResultView.setItems(searchResultData);

        //Event handlers
        propertyList.getSelectionModel().selectedItemProperty().addListener((o, oldVal, searchProperty) -> {
            searchInput.clear();
            setSearchProperty(searchProperty);
        });

        searchInput.textProperty().addListener((o, oldVal, searchTerm) -> {
            searchResultData.clear();
            if (!searchTerm.equals("")) {
                for (Person person : fullSetData) {
                    if (containsIgnoreCase(person.propertyMap.get(getSearchProperty()).getValue(), searchTerm)) {
                        searchResultData.add(person);
                        person.setSubLabelProperty(getSearchProperty());
                    }
                }
            }
        });


        //Setup for the user
        propertyList.getSelectionModel().select(0);
    }

    public boolean containsIgnoreCase(String str1, String str2) {
        return str1.toLowerCase().contains(str2.toLowerCase());
    }

    public String getSearchProperty() {
        return searchProperty.get();
    }

    public StringProperty searchPropertyProperty() {
        return searchProperty;
    }

    public void setSearchProperty(String searchProperty) {
        this.searchProperty.set(searchProperty);
    }
}
