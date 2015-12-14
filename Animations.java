package sample;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Created by Nick on 12/14/2015.
 */
public class Animations {
    public static Interpolator materialInterp = Interpolator.SPLINE(.62,.28,.23,.99);

    public static ScaleTransition FloatingActionButtonPressed = new ScaleTransition(Duration.millis(250));
    static {
        FloatingActionButtonPressed.setToX(1.2);
        FloatingActionButtonPressed.setToY(1.2);
        FloatingActionButtonPressed.setCycleCount(1);
        FloatingActionButtonPressed.setInterpolator(materialInterp);
    }
    public static ScaleTransition FloatingActionButtonReleased = new ScaleTransition(Duration.millis(250));
    static {
        FloatingActionButtonReleased.setToX(1);
        FloatingActionButtonReleased.setToY(1);
        FloatingActionButtonReleased.setCycleCount(1);
        FloatingActionButtonReleased.setInterpolator(materialInterp);
    }

    public static ScaleTransition FloatingActionButtonClicked = new ScaleTransition(Duration.millis(250));
    static {
        FloatingActionButtonClicked.setToX(0);
        FloatingActionButtonClicked.setToY(0);
        FloatingActionButtonClicked.setCycleCount(1);
        FloatingActionButtonClicked.setInterpolator(materialInterp);
    }

    public static ParallelTransition NewCard = new ParallelTransition();
    static {
        ScaleTransition cardScale = new ScaleTransition(Duration.millis(700));
        TranslateTransition cardTranslate = new TranslateTransition(Duration.millis(700));

        cardTranslate.setByX(-180);
        cardTranslate.setByY(-180);
        cardTranslate.setCycleCount(1);
        cardTranslate.setInterpolator(materialInterp);

        cardScale.setInterpolator(materialInterp);
        cardScale.setByX(1);
        cardScale.setByY(1);
        cardScale.setCycleCount(1);

        NewCard.getChildren().addAll(cardScale, cardTranslate);
    }

    public static RotateTransition SortIconClicked = new RotateTransition(Duration.millis(700));
    static {
        SortIconClicked.setInterpolator(Interpolator.SPLINE(.62, .28, .23, .99));
        SortIconClicked.setByAngle(180);
        SortIconClicked.setCycleCount(1);
    }
}
