package sample;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.util.Map;

/**
 * Created by Nick on 12/14/2015.
 */
public class ProfileCard extends VBox{
    protected JFXListView<Node> propertyList;
    protected VBox headerVbox;
    protected ImageView profileImage;
    protected IconCell name, phone, address;

    protected ObservableList<Node> listViewData = FXCollections.observableArrayList();
    protected IconCell[] cells;

    public ProfileCard() {
        super();
        HBox.setMargin(this, new Insets(10, 5, 5, 10));
        this.setPrefSize(385, 385);
        this.getStyleClass().add("card");

        HBox tempHBox = new HBox();
        setMargin(tempHBox, new Insets(0, -16, 0, -16));
        this.getChildren().add(tempHBox);

        headerVbox = new VBox();
        //headerVbox.setPrefSize(150, 178);
        headerVbox.setSpacing(10);
        HBox.setHgrow(headerVbox, Priority.ALWAYS);
        HBox.setMargin(headerVbox, new Insets(0, 0, 0, 16));
        tempHBox.getChildren().add(headerVbox);

        StackPane tempStackPane = new StackPane();
        //tempStackPane.setPrefHeight(75);
        HBox.setMargin(tempStackPane, new Insets(-16, 0, 0, 0));
        tempHBox.getChildren().add(tempStackPane);

        profileImage = new ImageView();
        profileImage.setFitHeight(160);
        profileImage.setFitWidth(160);
        profileImage.setPreserveRatio(true);
        tempStackPane.getChildren().add(profileImage);

        Circle tempCircle = new Circle();
        tempCircle.setFill(Paint.valueOf("#1f93ff00"));
        tempCircle.setRadius(75.0);
        tempCircle.setStroke(Paint.valueOf("#00000082"));
        tempCircle.setStrokeWidth(2.0);
        tempCircle.setStrokeType(StrokeType.INSIDE);
        tempCircle.setEffect(new DropShadow());
        tempStackPane.getChildren().add(tempCircle);

        propertyList = new JFXListView<>();
        VBox.setVgrow(propertyList, Priority.ALWAYS);
        this.getChildren().add(propertyList);

        initialize();
    }

    public void initialize() {
        propertyList.setCellFactory((list) -> {
            JFXListCell cell = new RudeCellCell();
            return cell;
        });
        propertyList.getStyleClass().add("mylistview");

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

        propertyList.setItems(listViewData);
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }
}
