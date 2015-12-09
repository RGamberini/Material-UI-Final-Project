package sample;

import com.jfoenix.controls.JFXListCell;

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
                this.setPickOnBounds(false);
                this.setOnMouseEntered((e) -> item.handleMouseEntered(e));
                this.setOnMouseExited((e) -> item.handleMouseEntered(e));
                this.setOnMouseClicked((e) -> item.handleMouseClick(e));
                //this.setOnMouseEntered((e) -> this.setOnMouseMoved((f) -> item.handleHoverAction(f)));
                //this.setOnMouseMoved((f) -> item.handleHoverAction(f));
                //this.setOnMouseExited((e) -> this.setOnMouseMoved(null));
                //this.setMouseTransparent(true);
            }
        }
    }
}
