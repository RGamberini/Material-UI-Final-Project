package sample;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.util.Duration;

/**
 * Created by Nick on 12/10/2015.
 */
public class RudeSortButton extends JFXButton {
    private BooleanProperty sortAscending;
    private BooleanProperty active;
    private MaterialDesignIconView sortIcon;
    private RotateTransition rotate;
    private StringProperty sortProperty;

    public RudeSortButton(boolean active) {
        super();
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getStyleClass().add("button-title");

        //Build the arrow Icon
        sortIcon = new MaterialDesignIconView();
        sortIcon.setGlyphName("ARROW_UP_BOLD");
        sortIcon.setSize("24");
        if (active) {
            sortIcon.getStyleClass().add("button-icon");
        } else {
            sortIcon.getStyleClass().add("inactive");
        }
        this.setGraphic(sortIcon);

        rotate = new RotateTransition(Duration.millis(300), sortIcon);
        rotate.setRate(1);
        rotate.setByAngle(180);
        rotate.setCycleCount(1);

        sortAscending = new SimpleBooleanProperty(true);
        this.active = new SimpleBooleanProperty(active);
        sortProperty = new SimpleStringProperty();

        this.setOnMouseClicked((e) -> {
            if (getActive()) {
                rotate.play();
                setSortAscending(!getSortAscending());
            } else {
                setActive(true);
                System.out.println("Fire");
                setSortAscending(getSortAscending());
            }
        });

        this.active.addListener((o, oldVal, newVal) -> {
            if (newVal) {
                IconCell.switchStyleClass(sortIcon, "inactive", "button-icon");
            } else {
                IconCell.switchStyleClass(sortIcon, "button-icon", "inactive");
            }
        });
    }

    public boolean getSortAscending() {
        return sortAscending.get();
    }

    public BooleanProperty sortAscendingProperty() {
        return sortAscending;
    }

    public void setSortAscending(boolean sortAscending) {
        this.sortAscending.set(sortAscending);
    }

    public boolean getActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public String getSortProperty() {
        return sortProperty.get();
    }

    public StringProperty sortPropertyProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        this.sortProperty.set(sortProperty);
    }
}
