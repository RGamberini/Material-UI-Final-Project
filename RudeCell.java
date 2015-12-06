package sample;

import com.jfoenix.controls.JFXListCell;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.transform.Transform;

/**
 * Created by Nick on 12/4/2015.
 */
public class RudeCell extends HBox{
    Label name;
    MaterialIconView edit, delete;
    MaterialIconView[] icons;

    public RudeCell(String item) {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        this.getStyleClass().add("rudecell");

        name = new Label(item);
        this.getChildren().add(name);
        name.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        name.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        this.setHgrow(name, Priority.ALWAYS);

        edit = new MaterialIconView();
        this.getChildren().add(edit);
        edit.setGlyphName("EDIT");
        edit.setGlyphSize(20);
        edit.setStyleClass("inactive");
        edit.setStyleClass("edit");
        edit.setStyleClass("invisible");
        this.setMargin(edit, new Insets(0,1,0,0));

        delete= new MaterialIconView();
        this.getChildren().add(delete);
        delete.setGlyphName("DELETE");
        delete.setGlyphSize(20);
        delete.setStyleClass("inactive");
        delete.setStyleClass("delete");
        delete.setStyleClass("invisible");

        icons = new MaterialIconView[]{edit, delete};

        //DEBUG
        this.setPickOnBounds(false);
        this.setMouseTransparent(true);
        edit.setPickOnBounds(false);
        edit.setMouseTransparent(true);
        delete.setPickOnBounds(false);
        delete.setMouseTransparent(true);
        //END DEBUG
    }

    public double vectorDistance(double x1, double y1, double x2, double y2) {
        double xd = x2-x1;
        double yd = y2-y1;
        return Math.sqrt(xd*xd + yd*yd);
    }

    private void switchStyleClass(Node node, String style1, String style2) {
        ObservableList<String> nodeStyle = node.getStyleClass();
        if (nodeStyle.contains(style1)) {
            nodeStyle.set(nodeStyle.indexOf(style1), style2);
            return;
        } else if (nodeStyle.contains(style2)) {
            nodeStyle.set(nodeStyle.indexOf(style2), style1);
            return;
        }
    }

    private final static int HOVERDISTANCE = 10;
    public void handleHoverAction(MouseEvent event) {
        for (MaterialIconView icon: icons) {
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
                switchStyleClass(icon, "inactive", "active");
            } else if (distance < HOVERDISTANCE && (style.contains("inactive") || this.getScene().getCursor() != Cursor.HAND)) {
                try {
                    this.getScene().setCursor(Cursor.HAND);
                } catch (NullPointerException e) {
                    System.err.println("Scene unavailable");
                }
                switchStyleClass(icon, "inactive", "active");
            }
        }
    }

    public void handleMouseEntered(MouseEvent event) {
        switchStyleClass(edit, "invisible", "visible");
        switchStyleClass(delete, "invisible", "visible");
    }
}
