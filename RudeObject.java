package sample;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.GlyphIcon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * Created by Nick on 12/16/2015.
 */
public abstract class RudeObject extends IconCell {
    StringProperty subLabelProperty;

    public Map<String, StringProperty> propertyMap;
    protected ObjectProperty<Image> profileImage;

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

    public abstract String getDEFAULT_SORT_PROPERTY();

    public abstract void initHeader(IconCell name, IconCell headerCell1, IconCell headerCell2);

    public abstract void initInputHeader(JFXTextField nameField, JFXTextField headerCell1Field, JFXTextField headerCell2Field);

    public abstract RudeObject randomInstance();
}
