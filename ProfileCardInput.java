package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.text.RuleBasedCollator;
import java.util.Map;

/**
 * Created by Nick on 12/14/2015.
 */
public class ProfileCardInput extends ProfileCard {
    private Person personToBe;
    public ProfileCardInput() {
        super();
        personToBe = new Person();
        profileImage.imageProperty().bind(personToBe.profileImageProperty());
        personToBe.setProfileImage(RandomPersonFactory.randomProfilePicture());

        name.getChildren().remove(name.mainLabel);
        name.setStyle("-fx-border-style: hidden hidden hidden hidden;");
        JFXTextField nameField = new JFXTextField();
        nameField.setPromptText("Name");
        name.getChildren().add(nameField);
        ChangeListener nameListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    nameField.textProperty().unbind();
                    nameField.textProperty().addListener((_o, _oldVal, _newVal) -> {
                        int i = _newVal.indexOf(" ");
                        if (i > -1 && i < _newVal.length() - 1) {
                            personToBe.setFirstName(_newVal.substring(0, i).trim());
                            personToBe.setLastName(_newVal.substring(i).trim());
                        } else {
                            personToBe.setFirstName(_newVal.trim());
                        }
                    });
                } else {
                    nameField.textProperty().bind(Bindings.concat(personToBe.firstNameProperty(), " ", personToBe.lastNameProperty()));
                }
            }
        };
        nameField.focusedProperty().addListener(nameListener);

        phone.getChildren().remove(phone.mainLabel);
        phone.setStyle("-fx-border-style: hidden hidden hidden hidden;");
        JFXTextField phoneField = new JFXTextField();
        personToBe.phoneProperty().bindBidirectional(phoneField.textProperty());
        phoneField.setPromptText("Phone");
        phone.getChildren().add(phoneField);

        address.getChildren().remove(address.mainLabel);
        address.setStyle("-fx-border-style: hidden hidden hidden hidden;");
        JFXTextField addressField = new JFXTextField();
        personToBe.homeAddressProperty().bindBidirectional(addressField.textProperty());
        addressField.setPromptText("Address");
        address.getChildren().add(addressField);

        for (Map.Entry<String, StringProperty> entry: personToBe.propertyMap.entrySet()) {
            IconCell cell = new IconCell();
            cell.mainLabel.setText(entry.getKey());
            cell.setExempt(true);
            HBox.setMargin(cell.mainLabel, new Insets(0, 0, 0, 16));

            JFXTextField subLabel = new JFXTextField();
            subLabel.setPromptText(entry.getKey());
            subLabel.textProperty().bindBidirectional(entry.getValue());
            cell.getChildren().add(subLabel);

            cell.setMargin(subLabel, new Insets(0, 5, 0, 5));
            listViewData.add(cell);
        }
        listViewData.sort(new RudeCellComparator("test"));

        //Buttons!
    }
}
