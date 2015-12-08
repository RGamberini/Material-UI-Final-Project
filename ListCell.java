package sample;

import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;

import java.util.ArrayList;

/**
 * This is a basic cell with an edit and delete button
 */
public class ListCell extends RudeCell {

    Label name;
    MaterialIconView edit, delete;
    ArrayList<MaterialIconView> icons;

    public ListCell(String item) {
        super();
        //        name = new Label(item.getFirstName() + " " + item.getLastName());
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

        icons = new ArrayList();
        icons.add(edit);
        icons.add(delete);
    }

    public static double vectorDistance(double x1, double y1, double x2, double y2) {
        double xd = x2-x1;
        double yd = y2-y1;
        return Math.sqrt(xd*xd + yd*yd);
    }

    public static void switchStyleClass(Node node, String style1, String style2) {
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
