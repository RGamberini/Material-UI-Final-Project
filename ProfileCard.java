package sample;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Created by Nick on 12/14/2015.
 */
public class ProfileCard extends VBox{
    protected JFXListView<Node> propertyList;
    protected VBox headerVbox;
    protected ImageView profileImage;
    protected IconCell name, headerCell1, headerCell2;
    protected StackPane avatarStackPane;

    protected ObservableList<Node> listViewData = FXCollections.observableArrayList();
    protected IconCell[] cells;

    public ProfileCard() {
        super();
        HBox.setMargin(this, new Insets(10, 5, 5, 10));
        this.setPrefSize(385, 385);
        this.setMaxSize(385, 385);
        this.getStyleClass().add("card");
        this.getStyleClass().add("profile-card");

        HBox tempHBox = new HBox();
        setMargin(tempHBox, new Insets(0, -16, 0, -16));
        this.getChildren().add(tempHBox);

        headerVbox = new VBox();
        //headerVbox.setPrefSize(150, 178);
        headerVbox.setSpacing(10);
        HBox.setHgrow(headerVbox, Priority.ALWAYS);
        HBox.setMargin(headerVbox, new Insets(0, 0, 0, 16));
        tempHBox.getChildren().add(headerVbox);

        avatarStackPane = new StackPane();
        //avatarStackPane.setPrefHeight(75);
        HBox.setMargin(avatarStackPane, new Insets(-16, 0, 0, 0));
        tempHBox.getChildren().add(avatarStackPane);

        profileImage = new ImageView();
        profileImage.setFitHeight(160);
        profileImage.setFitWidth(160);
        profileImage.setPreserveRatio(true);
        avatarStackPane.getChildren().add(profileImage);

        propertyList = new JFXListView<>();
        VBox.setVgrow(propertyList, Priority.ALWAYS);
        propertyList.setMaxHeight(209);
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

        headerCell1 = new IconCell();
        headerCell1.getStyleClass().add("profile-card-heading");

        headerCell2 = new IconCell();
        headerCell2.mainLabel.setWrapText(true);
        headerCell2.mainLabel.setTextOverrun(OverrunStyle.CLIP);
        headerCell2.getStyleClass().add("profile-card-heading");

        cells = new IconCell[]{name, headerCell1, headerCell2};
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
