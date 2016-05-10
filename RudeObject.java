package sample;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.GlyphIcon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Nick on 12/16/2015.
 */
public abstract class RudeObject extends IconCell {
    RudeProperty subLabelProperty;

    public Map<String, RudeProperty> propertyMap;
    protected ObjectProperty<Image> profileImage;
    protected Map<String, ObservableList<RudeObject>> extraLists;
    protected IconCell subLabel;
    public Class<? extends RudeObject> extraListClass;

    protected void layoutInit() {
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

    public String getSubLabelProperty() {
        return subLabelProperty.get();
    }

    public RudeProperty subLabelPropertyProperty() {
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

    public abstract String getDEFAULT_MAIN_SORT_PROPERTY();

    public abstract String getDEFAULT_SUB_SORT_PROPERTY();

    public abstract void initHeader(IconCell name, IconCell headerCell1, IconCell headerCell2);

    public abstract void initInputHeader(JFXTextField nameField, JFXTextField headerCell1Field, JFXTextField headerCell2Field);

    public abstract RudeObject randomInstance();

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
}
