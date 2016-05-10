package sample;

import com.jfoenix.controls.JFXListCell;
import javafx.scene.Node;

/**
 * Created by Nick on 12/6/2015.
 */
public class RudeCellCell extends JFXListCell<Node> {
    public RudeCellCell() {
        super();
    }

    public void updateItem(Node item, boolean empty) {
        if (item != null) {
            if (item instanceof RudeCell) {
                if (((RudeCell) item).isExempt()) {
                    setGraphic(item);
                } else {
                    super.updateItem(item, empty);
                    ((RudeCell) item).setInList(true);
                    this.setPickOnBounds(false);
                    this.setOnMouseEntered(item::fireEvent);
                    this.setOnMouseExited(item::fireEvent);
                    this.setOnMouseMoved(item::fireEvent);
                }
            } else {
                super.updateItem(item, empty);
            }
        }
    }
}
