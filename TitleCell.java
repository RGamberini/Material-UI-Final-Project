package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.RotateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

/**
 * Created by Nick on 12/8/2015.
 */
public class TitleCell  extends RudeCell{
    public JFXButton mainText;
    public JFXRippler mainTextRippler;
    public MaterialDesignIconView sort1;
    public RotateTransition rotate;
    public BooleanProperty sort_ascending;

    public TitleCell(String mainText) {
        super();
        this.setExempt(true);

        this.sort_ascending = new SimpleBooleanProperty(true);

        this.mainText = new JFXButton(mainText);
        this.setHgrow(this.mainText, Priority.ALWAYS);
        this.mainText.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.mainText.setAlignment(Pos.CENTER_LEFT);
        this.mainText.getStyleClass().add("button-title");

        sort1 = new MaterialDesignIconView();
        sort1.setGlyphName("ARROW_UP_BOLD");
        sort1.setSize("24");
        sort1.setStyleClass("button-icon");
        this.mainText.setGraphic(sort1);

        rotate = new RotateTransition(Duration.millis(300), sort1);
        rotate.setRate(1);
        rotate.setByAngle(180);
        rotate.setCycleCount(1);

        this.mainText.setOnMouseClicked((e) -> {
            rotate.play();
            this.setSort_ascending(!this.getSort_ascending());
            System.out.println("I know you wouldn't but I have to check");
        });

        this.getChildren().add(this.mainText);
    }

    public boolean getSort_ascending() {
        return sort_ascending.get();
    }

    public BooleanProperty sort_ascendingProperty() {
        return sort_ascending;
    }

    public void setSort_ascending(boolean sort_ascending) {
        this.sort_ascending.set(sort_ascending);
    }
}
