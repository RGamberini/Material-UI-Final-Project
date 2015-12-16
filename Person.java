package sample;

import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.collections.MappingChange;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
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
public class Person extends IconCell{
    private StringProperty firstName, lastName, phone, homeAddress, subLabelProperty;
    private ObjectProperty<Image> profileImage;
    public Map<String, StringProperty> propertyMap;
    public static ArrayList<String> properties;
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

    static {
        properties = new ArrayList<>();
        properties.add("First Name");
        properties.add("Last Name");
        properties.add("Phone");
        properties.add("Home Address");
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

    public String getSubLabelProperty() {
        return subLabelProperty.get();
    }

    public StringProperty subLabelPropertyProperty() {
        return subLabelProperty;
    }

    public void setSubLabelProperty(String subLabelProperty) {
        this.subLabelProperty.set(subLabelProperty);
    }

    public Image getProfileImage() {
        return profileImage.get();
    }

    public ObjectProperty<Image> profileImageProperty() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage.set(profileImage);
    }
}
