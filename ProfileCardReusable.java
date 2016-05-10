package sample;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Nick on 12/14/2015.
 */
public class ProfileCardReusable extends ProfileCard {
    private ObjectProperty<RudeObject> mainRudeObject;
    private TabPane tabPane;
    public ProfileCardReusable() {
        super();
        mainRudeObject = new SimpleObjectProperty<>();
        mainRudeObjectProperty().addListener((o, oldVal, newVal) -> changeCard(newVal));
    }

    private void changeCard(RudeObject person) {
        person.initHeader(name, headerCell1, headerCell2);
        profileImage.imageProperty().bind(person.profileImageProperty());

        listViewData.clear();
        if (person.extraLists != null) {
            this.getChildren().remove(tabPane);
            this.getChildren().remove(propertyList);
            tabPane = new JFXTabPane();
            this.getChildren().add(tabPane);
            Tab newTabe = new Tab();
            newTabe.setText("Properties");
            tabPane.getTabs().add(newTabe);
            newTabe.setContent(propertyList);

            for(Map.Entry<String, ObservableList<RudeObject>> entry: person.extraLists.entrySet()) {
                Tab newTab = new Tab();
                tabPane.getTabs().add(newTab);
                newTab.setText(entry.getKey());
                JFXListView newListView = new JFXListView();
                newListView.setItems(entry.getValue());
                newTab.setContent(newListView);
            }
        }
        for (Map.Entry<String, RudeProperty> entry: person.propertyMap.entrySet()) {
            IconCell cell = new IconCell();
            cell.mainLabel.setText(entry.getKey());
            Label subLabel = new Label();
            subLabel.textProperty().bind(entry.getValue());
            cell.getChildren().add(subLabel);

            if(!entry.getValue().isReadOnly()) {
                RudeEditIcon edit = new RudeEditIcon(cell, subLabel, entry.getValue());
                cell.icons.add(edit);
                cell.setMargin(subLabel, new Insets(0, 5, 0, 5));
            } else {
                cell.setMargin(subLabel, new Insets(0, 25, 0, 0));
            }
            listViewData.add(cell);
        }
    }
    public RudeObject getMainRudeObject() {
        return mainRudeObject.get();
    }

    public ObjectProperty<RudeObject> mainRudeObjectProperty() {
        return mainRudeObject;
    }

    public void setMainRudeObject(RudeObject mainRudeObject) {
        this.mainRudeObject.set(mainRudeObject);
    }
}
