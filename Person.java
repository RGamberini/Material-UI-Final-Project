package sample;

import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.collections.MappingChange;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nick on 12/9/2015.
 */
public class Person extends RudeObject {
    private StringProperty firstName, lastName, phone, homeAddress;
    private IconCell subLabel;
    private static int c = 0;

    public Person() {
        super();
        //Initialization stuff
        propertyMap = new HashMap<>();

        this.firstName = new SimpleStringProperty();
        propertyMap.put("First Name", firstNameProperty());

        this.lastName = new SimpleStringProperty();
        propertyMap.put("Last Name", lastNameProperty());

        this.phone = new SimpleStringProperty();
        propertyMap.put("Phone", phoneProperty());

        this.homeAddress = new SimpleStringProperty();
        propertyMap.put("Home Address", homeAddressProperty());

        this.subLabelProperty = new SimpleStringProperty();
        this.profileImage = new SimpleObjectProperty<>();

        layoutInit();
    }

    public Person(String firstName, String lastName, String phone, String homeAddress, Image profileImage) {
        this();

        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setHomeAddress(homeAddress);
        setProfileImage(profileImage);
        //End Initialization stuff
    }

    private void layoutInit() {
        //The down and dirty
        this.mainLabel.textProperty().bind(Bindings.concat(firstNameProperty(), " ", lastNameProperty()));

        RudeIcon edit = new RudeEditIcon(this, mainLabel) {
            @Override
            public void assignText(String text) {
                int i = text.indexOf(" ");
                setFirstName(text.substring(0, i));
                setLastName(text.substring(i + 1));
            }
        };
        this.icons.add(edit);
        this.setMargin(edit, new Insets(0, 1, 0, 0));

        RudeIcon delete = new RudeDeleteIcon(this);
        this.icons.add(delete);

        subLabel = new IconCell();
        subLabel.setInList(true);
        RudeIcon subEdit = new RudeEditIcon(subLabel, subLabel.mainLabel) {
            @Override
            public void assignText(String text) {
                propertyMap.get(getSubLabelProperty()).set(text);
            }
        };
        subLabel.icons.add(subEdit);
        this.getChildren().add(subLabel);
        subLabel.setMargin(subLabel.mainLabel, new Insets(0, 5, 0, 15));

        this.subLabelPropertyProperty().addListener((o, oldVal, newVal) -> {
            subLabel.mainLabel.textProperty().bind(this.propertyMap.get(newVal));
        });
    }
    public void initHeader() {

    }

    @Override
    public void initHeader(IconCell name, IconCell headerCell1, IconCell headerCell2) {
        name.mainLabel.textProperty().bind(Bindings.concat(this.firstNameProperty(), " ", this.lastNameProperty()));
        name.icons.clear();
        name.icons.add(new RudeEditIcon(name, name.mainLabel) {
            @Override
            public void assignText(String text) {
                int i = text.indexOf(" ");
                setFirstName(text.substring(0, i));
                setLastName(text.substring(i + 1));
            }
        });

        headerCell1.mainLabel.textProperty().bind(this.phoneProperty());
        headerCell1.icons.clear();
        headerCell1.icons.add(new RudeEditIcon(headerCell1, headerCell1.mainLabel, this.phoneProperty()));

        FontAwesomeIconView phoneIcon = new FontAwesomeIconView(FontAwesomeIcon.PHONE_SQUARE);
        phoneIcon.setStyleClass("profile-card-icon");
        phoneIcon.setSize("22");
        headerCell1.mainLabel.setGraphic(phoneIcon);

        headerCell2.mainLabel.textProperty().bind(this.homeAddressProperty());
        headerCell2.icons.clear();
        headerCell2.icons.add(new RudeEditIcon(headerCell2, headerCell2.mainLabel, this.homeAddressProperty()));

        MaterialIconView addressIcon = new MaterialIconView(MaterialIcon.PLACE);
        addressIcon.setStyleClass("profile-card-icon");
        addressIcon.setSize("25");
        headerCell2.mainLabel.setGraphic(addressIcon);
    }

    @Override
    public void initInputHeader(JFXTextField nameField, JFXTextField headerCell1Field, JFXTextField headerCell2Field) {
        setFirstName("");
        setLastName("");
        ChangeListener nameListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    nameField.textProperty().unbind();
                    nameField.textProperty().addListener((_o, _oldVal, _newVal) -> {
                        int i = _newVal.indexOf(" ");
                        if (i > -1 && i < _newVal.length() - 1) {
                            setFirstName(_newVal.substring(0, i).trim());
                            setLastName(_newVal.substring(i).trim());
                        } else {
                            setFirstName(_newVal.trim());
                            setLastName("");
                        }
                    });
                } else {
                    nameField.textProperty().bind(Bindings.concat(firstNameProperty(), " ", lastNameProperty()));
                }
            }
        };
        nameField.focusedProperty().addListener(nameListener);

        phoneProperty().bindBidirectional(headerCell1Field.textProperty());
        headerCell1Field.setPromptText("Phone");

        homeAddressProperty().bindBidirectional(headerCell2Field.textProperty());
        headerCell2Field.setPromptText("Address");
    }

    @Override
    public String getDEFAULT_SORT_PROPERTY() {
        return "Last Name";
    }

    @Override
    public RudeObject randomInstance() {
        return RandomPersonFactory.randomPerson();
    }

    @Override
    public void handleHoverAction(MouseEvent event) {
        super.handleHoverAction(event);
        this.subLabel.handleHoverAction(event);
    }

    @Override
    public void handleMouseClick(MouseEvent event) {
        super.handleMouseClick(event);
        subLabel.handleMouseClick(event);
    }

    @Override
    public void handleMouseEntered(MouseEvent event) {
        super.handleMouseEntered(event);
        this.subLabel.handleMouseEntered(event);
    }

    @Override
    public void handleMouseExited(MouseEvent event) {
        super.handleMouseExited(event);
        this.subLabel.handleMouseExited(event);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getHomeAddress() {
        return homeAddress.get();
    }

    public StringProperty homeAddressProperty() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress.set(homeAddress);
    }
}
