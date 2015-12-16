package sample;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
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
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Collection;

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

        //Setup arraylist
        icons = new ArrayList<RudeIcon>() {
            @Override
            public boolean add(RudeIcon rudeIcon) {
                getChildren().add(rudeIcon);
                return super.add(rudeIcon);
            }

            @Override
            public boolean addAll(Collection c) {
                getChildren().addAll(c);
                return super.addAll(c);
            }

            @Override
            public void clear() {
                ArrayList<Node> toDelete = new ArrayList<>();
                for(Node node: getChildren()) {
                    if (node instanceof RudeIcon) {
                        toDelete.add(node);
                    }
                }
                getChildren().removeAll(toDelete);
                super.clear();
            }
        };

        //Setup Listeners
        this.setOnMouseEntered((e) -> handleMouseEntered(e));
        this.setOnMouseExited((e) -> handleMouseExited(e));
        this.setOnMouseClicked((e) -> handleMouseClick(e));
        this.setOnMouseMoved((e) -> handleHoverAction(e));
    }

    public static double vectorDistance(double x1, double y1, double x2, double y2) {
        double xd = x2 - x1;
        double yd = y2 - y1;
        return Math.sqrt(xd * xd + yd * yd);
    }

    public static void switchStyleClass(Node node, String style1, String style2) {
        ObservableList<String> nodeStyle = node.getStyleClass();
        if (nodeStyle.contains(style1)) {
            nodeStyle.removeAll(style1);
            nodeStyle.add(style2);
            return;
        }
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
                changeCursor(Cursor.DEFAULT);
                switchStyleClass(icon, "active", "inactive");
            } else if (distance < HOVERDISTANCE && (style.contains("inactive") || this.getScene().getCursor() != Cursor.HAND)) {
                changeCursor(Cursor.HAND);
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
        changeCursor(Cursor.DEFAULT);
    }

    public void handleMouseClick(MouseEvent event) {
        for (RudeIcon icon : icons) {
            if (icon.getStyleClass().contains("active")) {
                icon.handleMouseClick(event);
            }
        }
    }

    public void changeCursor(Cursor cursor) {
        try {
            if (!isInList()) {
                this.setCursor(cursor);
            } else {
                this.getScene().setCursor(cursor);
            }
        } catch (NullPointerException e) {
            System.err.println("Scene unavailable");
        }
    }

    @Override
    public String toString() {
        return mainLabel.getText();
    }
}