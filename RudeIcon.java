package sample;

import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nick on 12/8/2015.
 */
public class RudeIcon extends MaterialIconView{
    public RudeIcon() {
        super();
        setStyleClass("inactive");
        setStyleClass("invisible");
    }

    public void handleMouseClick(MouseEvent event, IconCell parent) {
        return;
    }
}
