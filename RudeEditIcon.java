package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;

import javax.swing.*;

/**
 * Created by Nick on 12/9/2015.
 */
public class RudeEditIcon  extends RudeIcon{
    private Label toEdit;
    private RudeProperty toEditProperty = null;
    private JFXTextField newField = null;
    private int index = -1;
    private EventHandler<MouseEvent> handler;
    private Scene scene = null;

    public RudeEditIcon(IconCell iconCell, Label toEdit) {
        super(iconCell);

        this.toEdit = toEdit;
        this.setGlyphName("EDIT");
        this.setGlyphSize(20);
        this.setStyleClass("inactive");
        this.setStyleClass("edit");
        this.setStyleClass("invisible");

        handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close();
            }
        };
    }

    public RudeEditIcon(IconCell iconCell, Label toEdit, RudeProperty toEditProperty) {
        this(iconCell, toEdit);
        this.toEditProperty = toEditProperty;
    }

    @Override
    public void handleMouseClick(MouseEvent event) {
        super.handleMouseClick(event);
        //iconCell.setExempt(true);

        index = iconCell.getChildren().indexOf(toEdit);
        iconCell.getChildren().remove(toEdit);

        newField = new JFXTextField(toEdit.getText());
        iconCell.getChildren().add(index, newField);
        iconCell.setHgrow(newField, Priority.ALWAYS);
        newField.requestFocus();

        newField.setOnKeyPressed((e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                close();
            }
        });

        System.out.println("got this far");
        scene = this.getScene();
        this.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
    }

    public void assignText(String text) {
        if (toEditProperty == null) {
            toEdit.setText(text);
        } else {
            toEditProperty.set(text);
        }
    }

    public void close() {
        iconCell.getChildren().remove(newField);
        assignText(newField.getText());
        iconCell.getChildren().add(index, toEdit);
        scene.removeEventFilter(MouseEvent.MOUSE_CLICKED, handler);
        scene =null;
    }

    public boolean isClosed() {
        for(Node child: iconCell.getChildren()) {

        }
        return false;
    }
}
