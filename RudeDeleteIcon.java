package sample;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import sample.RudeIcon;

/**
 * Created by Nick on 12/10/2015.
 */
public class RudeDeleteIcon extends RudeIcon {
    public RudeDeleteIcon(IconCell parent) {
        super(parent);
        this.setGlyphName("DELETE");
        this.setGlyphSize(20);
        this.setStyleClass("delete");
    }

    @Override
    public void handleMouseClick(MouseEvent event) {
        iconCell.setToDelete(true);
    }
}
