package sample;

import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Created by Nick on 12/4/2015.
 */
public abstract class RudeCell extends HBox{
    private boolean exempt;
    public RudeCell() {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
        this.getStyleClass().add("rudecell");
        this.setExempt(false);
    }

    public void handleHoverAction(MouseEvent event) {}

    public void handleMouseEntered(MouseEvent event) {}

    public void handleMouseClick(MouseEvent event) {}

    public boolean isExempt() {
        return exempt;
    }

    public void setExempt(boolean exempt) {
        this.exempt = exempt;
    }
}
