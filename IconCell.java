package sample;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;

import java.util.ArrayList;

/**
 * This is a cell that contains icons
 */
public class IconCell extends RudeCell {

    Label mainLabel;
    ArrayList<RudeIcon> icons;

    public IconCell() {
        super();
        mainLabel = new Label();
        this.getChildren().add(mainLabel);
        mainLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        mainLabel.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        this.setHgrow(mainLabel, Priority.ALWAYS);
        icons = new ArrayList<>();
    }

    public static double vectorDistance(double x1, double y1, double x2, double y2) {
        double xd = x2 - x1;
        double yd = y2 - y1;
        return Math.sqrt(xd * xd + yd * yd);
    }

    public static void switchStyleClass(Node node, String style1, String style2) {
        ObservableList<String> nodeStyle = node.getStyleClass();
        if (nodeStyle.contains(style1)) {
            nodeStyle.set(nodeStyle.indexOf(style1), style2);
            return;
        }/* else if (nodeStyle.contains(style2)) {
            nodeStyle.set(nodeStyle.indexOf(style2), style1);
            return;
        }*/
    }


    private final static int HOVERDISTANCE = 10;

    public void handleHoverAction(MouseEvent event) {
        for (RudeIcon icon : icons) {
            Point2D point = icon.localToScene(0.0, 0.0);
            double x = point.getX() + 7, y = point.getY() - 10;
            //        System.out.println("EVENT: " + event.getSceneX() + " " + event.getSceneY());
            //        System.out.println("EDIT: " + x + " " + y);
            double distance = vectorDistance(event.getSceneX(), event.getSceneY(), x, y);
            //        System.out.println(distance);
            ObservableList<String> style = icon.getStyleClass();

            if (distance > HOVERDISTANCE && style.contains("active")) {
                try {
                    this.getScene().setCursor(Cursor.DEFAULT);
                } catch (NullPointerException e) {
                    System.err.println("Scene unavailable");
                }
                switchStyleClass(icon, "active", "inactive");
            } else if (distance < HOVERDISTANCE && (style.contains("inactive") || this.getScene().getCursor() != Cursor.HAND)) {
                try {
                    this.getScene().setCursor(Cursor.HAND);
                } catch (NullPointerException e) {
                    System.err.println("Scene unavailable");
                }
                switchStyleClass(icon, "inactive", "active");
                //switchStyleClass(icon, "invisible", "visible");
            }
        }
    }

    public void handleMouseEntered(MouseEvent event) {
        for (RudeIcon icon : icons) {
            switchStyleClass(icon, "invisible", "visible");
        }
    }

    public void handleMouseExited(MouseEvent event) {
        for (RudeIcon icon : icons) {
            switchStyleClass(icon, "visible", "invisible");
        }
    }

    public void handleMouseClick(MouseEvent event) {
        for (RudeIcon icon : icons) {
            if (icon.getStyleClass().contains("active")) {
                icon.handleMouseClick(event, this);
            }
        }
    }

    @Override
    public String toString() {
        return mainLabel.getText();
    }
}