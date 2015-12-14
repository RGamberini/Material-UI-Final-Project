package sample;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Nick on 12/11/2015.
 */
public class ProfileCardController {
    @FXML private JFXListView<Node> profileCardList = new JFXListView<>();
    @FXML private VBox headerVbox = new VBox();
    @FXML private ImageView profileImage = new ImageView();
    @FXML private IconCell name, phone, address;

    private ObservableList<Node> listViewData = FXCollections.observableArrayList();
    private ObjectProperty<Person> mainPerson;
    private IconCell[] cells;

    @FXML
    public void initialize() {
        mainPerson = new SimpleObjectProperty<>();
        profileCardList.setCellFactory((list) -> {
            JFXListCell cell = new RudeCellCell();
            return cell;
        });
        profileCardList.getStyleClass().add("mylistview");

        name = new IconCell();
        name.getStyleClass().add("profile-card-name");

        phone = new IconCell();
        phone.getStyleClass().add("profile-card-heading");
        FontAwesomeIconView phoneIcon = new FontAwesomeIconView(FontAwesomeIcon.PHONE_SQUARE);
        phoneIcon.setStyleClass("profile-card-icon");
        phoneIcon.setSize("22");
        phone.mainLabel.setGraphic(phoneIcon);

        address = new IconCell();
        address.mainLabel.setWrapText(true);
        address.mainLabel.setTextOverrun(OverrunStyle.CLIP);
        address.getStyleClass().add("profile-card-heading");
        MaterialIconView addressIcon = new MaterialIconView(MaterialIcon.PLACE);
        addressIcon.setStyleClass("profile-card-icon");
        addressIcon.setSize("25");
        address.mainLabel.setGraphic(addressIcon);

        cells = new IconCell[]{name, phone, address};
        this.headerVbox.getChildren().addAll(cells);

        mainPersonProperty().addListener((o, oldVal, newVal) -> changeCard(newVal));
//        phone.sceneProperty().addListener((o, oldVal, newVal) -> {
//            System.out.println("SCENE IS NULL: " + (newVal == null));
//        });
        profileCardList.setItems(listViewData);
    }

    private void changeCard(Person person) {
        name.mainLabel.textProperty().bind(Bindings.concat(person.firstNameProperty(), " ", person.lastNameProperty()));
        //name.mainLabel.textProperty().setValue(name.getScene().toString());

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

    public ImageView getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }
}
