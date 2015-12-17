package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

import java.util.Map;

/**
 * Created by Nick on 12/14/2015.
 */
public class ProfileCardReusable extends ProfileCard {
    private ObjectProperty<RudeObject> mainRudeObject;
    public ProfileCardReusable() {
        super();
        mainRudeObject = new SimpleObjectProperty<>();
        mainRudeObjectProperty().addListener((o, oldVal, newVal) -> changeCard(newVal));
    }

    private void changeCard(RudeObject person) {
        person.initHeader(name, headerCell1, headerCell2);
        profileImage.imageProperty().bind(person.profileImageProperty());

        listViewData.clear();
        for (Map.Entry<String, StringProperty> entry: person.propertyMap.entrySet()) {
            IconCell cell = new IconCell();
            cell.mainLabel.setText(entry.getKey());
            Label subLabel = new Label();
            subLabel.textProperty().bind(entry.getValue());
            cell.getChildren().add(subLabel);

            RudeEditIcon edit = new RudeEditIcon(cell, subLabel, entry.getValue());

            cell.icons.add(edit);
            cell.setMargin(subLabel, new Insets(0, 5, 0, 5));
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
