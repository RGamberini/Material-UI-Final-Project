package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Set;

/**
 * Created by Nick on 12/16/2015.
 */
public class SearchCard extends VBox {
    private JFXComboBox<String> propertyList;
    private JFXTextField searchInput;
    private JFXListView searchResultView;
    private ObservableList<RudeObject> searchResultData;
    private RudeProperty searchProperty;

    public SearchCard(ObservableList<RudeObject> fullSetData, Set<String> properties) {
        super();
        searchProperty = new RudeProperty();

        HBox.setMargin(this, new Insets(10, 5, 5, 10));
        this.setPrefSize(385, 385);
        this.getStyleClass().add("card");

        HBox tempHbox = new HBox();
        this.getChildren().add(tempHbox);

        propertyList = new JFXComboBox<>();
        propertyList.getItems().addAll(properties);
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
                for (RudeObject person : fullSetData) {
                    if (containsIgnoreCase(person.propertyMap.get(getSearchProperty()).getValue(), searchTerm)) {
                        searchResultData.add(person);
                        person.setSubLabelProperty(getSearchProperty());
                    }
                }
            }
        });

        JFXButton done = new JFXButton("Done");
        done.getStyleClass().add("button-cancel");
        done.setPadding(new Insets(0, 35, 0, 35));
        VBox.setMargin(done, new Insets(10, 0 ,0 ,0));
        this.setAlignment(Pos.BOTTOM_RIGHT);
        this.getChildren().add(done);
        done.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->onCardClearProperty.get().handle(new FloatingCardEvent(FloatingCardEvent.CLEAR)));


        //Setup for the user
        propertyList.getSelectionModel().select(0);
    }

    public boolean containsIgnoreCase(String str1, String str2) {
        return str1.toLowerCase().contains(str2.toLowerCase());
    }

    public String getSearchProperty() {
        return searchProperty.get();
    }

    public RudeProperty searchPropertyProperty() {
        return searchProperty;
    }

    public void setSearchProperty(String searchProperty) {
        this.searchProperty.set(searchProperty);
    }

    private ObjectProperty<EventHandler<? super FloatingCardEvent>> onCardClearProperty = new SimpleObjectProperty<>((clear)-> {});

    public EventHandler<? super FloatingCardEvent> getOnClear() {
        return onCardClearProperty.get();
    }

    public void setOnClear(EventHandler<? super FloatingCardEvent> handler) {
        this.onCardClearProperty.set(handler);
    }
}
