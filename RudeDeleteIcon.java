package sample;

import javafx.scene.input.MouseEvent;
import sample.RudeIcon;

/**
 * Created by Nick on 12/10/2015.
 */
public class RudeDeleteIcon extends RudeIcon {
    public RudeDeleteIcon() {
        super();
        this.setGlyphName("DELETE");
        this.setGlyphSize(20);
        this.setStyleClass("delete");
    }

    @Override
    public void handleMouseClick(MouseEvent event, IconCell parent) {
        parent.setToDelete(true);
    }
}
