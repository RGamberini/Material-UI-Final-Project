package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;

/**
 * Created by Nick on 12/9/2015.
 */
public class RudeEditIcon  extends RudeIcon{
    Label toEdit;

    public RudeEditIcon(Label toEdit) {
        super();

        this.toEdit = toEdit;
        this.setGlyphName("EDIT");
        this.setGlyphSize(20);
        this.setStyleClass("inactive");
        this.setStyleClass("edit");
        this.setStyleClass("invisible");
    }

    @Override
    public void handleMouseClick(MouseEvent event, IconCell parent) {
        super.handleMouseClick(event, parent);

        int index = parent.getChildren().indexOf(toEdit);
        parent.getChildren().remove(toEdit);

        JFXTextField newField = new JFXTextField(toEdit.getText());
        parent.getChildren().add(index, newField);
        parent.setHgrow(newField, Priority.ALWAYS);
        newField.requestFocus();

        newField.setOnKeyPressed((e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                parent.getChildren().remove(newField);
                assignText(newField.getText());
                parent.getChildren().add(index, toEdit);
            }
        });
    }

    public void assignText(String text) {
        toEdit.setText(text);
    }
}
