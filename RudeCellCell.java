package sample;

import com.jfoenix.controls.JFXListCell;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Created by Nick on 12/6/2015.
 */
public class RudeCellCell extends JFXListCell<RudeCell>{
    public RudeCellCell() {
        super();
    }

    public void updateItem(RudeCell item, boolean empty) {
        if (item != null) {
            if (item.isExempt()) {
                setGraphic(item);
            } else {
                super.updateItem(item, empty);
                item.setInList(true);
                this.setPickOnBounds(false);
                this.setOnMouseEntered(item::fireEvent);
                this.setOnMouseExited(item::fireEvent);
                this.setOnMouseMoved(item::fireEvent);
            }
        }
    }
}
