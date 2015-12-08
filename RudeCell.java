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

import java.util.ArrayList;

/**
 * Created by Nick on 12/4/2015.
 */
public abstract class RudeCell extends HBox{

    public RudeCell() {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        this.getStyleClass().add("rudecell");
    }

    public void handleHoverAction(MouseEvent event) {}

    public void handleMouseEntered(MouseEvent event) {}
}
