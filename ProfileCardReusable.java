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
    private ObjectProperty<Person> mainPerson;
    public ProfileCardReusable() {
        super();
        mainPerson = new SimpleObjectProperty<>();
        mainPersonProperty().addListener((o, oldVal, newVal) -> changeCard(newVal));
    }

    private void changeCard(Person person) {
        name.mainLabel.textProperty().bind(Bindings.concat(person.firstNameProperty(), " ", person.lastNameProperty()));
        name.icons.clear();
        name.icons.add(new RudeEditIcon(name, name.mainLabel) {
            @Override
            public void assignText(String text) {
                int i = text.indexOf(" ");
                person.setFirstName(text.substring(0, i));
                person.setLastName(text.substring(i + 1));
            }
        });

        phone.mainLabel.textProperty().bind(person.phoneProperty());
        phone.icons.clear();
        phone.icons.add(new RudeEditIcon(phone, phone.mainLabel, person.phoneProperty()));

        address.mainLabel.textProperty().bind(person.homeAddressProperty());
        address.icons.clear();
        address.icons.add(new RudeEditIcon(address, address.mainLabel, person.homeAddressProperty()));

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
    public Person getMainPerson() {
        return mainPerson.get();
    }

    public ObjectProperty<Person> mainPersonProperty() {
        return mainPerson;
    }

    public void setMainPerson(Person mainPerson) {
        this.mainPerson.set(mainPerson);
    }
}
