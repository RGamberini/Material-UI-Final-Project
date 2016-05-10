package sample;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Created by Nick on 12/9/2015.
 */
public class Person extends RudeObject {
    private RudeProperty firstName;
    private RudeProperty lastName;
    private RudeProperty phone;
    private RudeProperty homeAddress;
    private static int c = 0;

    public Person() {
        super();
        //Initialization stuff
        propertyMap = new HashMap<>();

        this.firstName = new RudeProperty();
        propertyMap.put("First Name", firstNameProperty());

        this.lastName = new RudeProperty();
        propertyMap.put("Last Name", lastNameProperty());

        this.phone = new RudeProperty();
        propertyMap.put("Phone", phoneProperty());

        this.homeAddress = new RudeProperty();
        propertyMap.put("Home Address", homeAddressProperty());

        RudeProperty _id = new RudeProperty(Integer.toString(c++));
        _id.setReadOnly(true);
        propertyMap.put("ID", _id);

        this.subLabelProperty = new RudeProperty();
        this.profileImage = new SimpleObjectProperty<>();
        setProfileImage(RandomRudeObjectFactory.randomProfilePicture());

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

    protected void layoutInit() {
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

        super.layoutInit();
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
        name.mainLabel.setStyle("-fx-font-size: 20px");

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
    public String getDEFAULT_MAIN_SORT_PROPERTY() {
        return "Last Name";
    }

    @Override
    public String getDEFAULT_SUB_SORT_PROPERTY() {
        return "First Name";
    }

    @Override
    public RudeObject randomInstance() {
        return RandomRudeObjectFactory.randomPerson();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public RudeProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public RudeProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhone() {
        return phone.get();
    }

    public RudeProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getHomeAddress() {
        return homeAddress.get();
    }

    public RudeProperty homeAddressProperty() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress.set(homeAddress);
    }
}
