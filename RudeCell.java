package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Created by Nick on 12/4/2015.
 */
public abstract class RudeCell extends HBox{
    private boolean exempt;
    private boolean inList;
    BooleanProperty toDelete;

    public RudeCell() {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        this.getStyleClass().add("rudecell");
        this.setExempt(false);

        toDelete = new SimpleBooleanProperty();
        this.setToDelete(false);
    }

    public void handleHoverAction(MouseEvent event) {}

    public void handleMouseEntered(MouseEvent event) {}

    public void handleMouseExited(MouseEvent event) {}

    public void handleMouseClick(MouseEvent event) {}

    public boolean isExempt() {
        return exempt;
    }

    public void setExempt(boolean exempt) {
        this.exempt = exempt;
    }

    public boolean getToDelete() {
        return toDelete.get();
    }

    public BooleanProperty toDeleteProperty() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete.set(toDelete);
    }

    public boolean isInList() {
        return inList;
    }

    public void setInList(boolean inList) {
        this.inList = inList;
    }
}
