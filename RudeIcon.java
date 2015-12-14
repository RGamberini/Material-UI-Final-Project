package sample;

import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nick on 12/8/2015.
 */
public class RudeIcon extends MaterialIconView{
    protected IconCell iconCell;
    public RudeIcon(IconCell parent) {
        super();
        setStyleClass("inactive");
        setStyleClass("invisible");
        this.iconCell = parent;
    }

    public void handleMouseClick(MouseEvent event) {
        return;
    }
}
