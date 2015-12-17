package sample;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Created by Nick on 12/14/2015.
 */
public class Animations {
    public static Interpolator materialInterp = Interpolator.SPLINE(.62,.28,.23,.99);

    public static ScaleTransition newFloatingActionButtonPressedAnimation(Node node) {
        ScaleTransition FloatingActionButtonPressed = new ScaleTransition(Duration.millis(250), node);
        FloatingActionButtonPressed.setToX(1.2);
        FloatingActionButtonPressed.setToY(1.2);
        FloatingActionButtonPressed.setCycleCount(1);
        FloatingActionButtonPressed.setInterpolator(materialInterp);
        return FloatingActionButtonPressed;
    }
    public static ScaleTransition newFloatingActionButtonReleasedAnimation(Node node) {
        ScaleTransition FloatingActionButtonReleased = new ScaleTransition(Duration.millis(250), node);
        FloatingActionButtonReleased.setToX(1);
        FloatingActionButtonReleased.setToY(1);
        FloatingActionButtonReleased.setCycleCount(1);
        FloatingActionButtonReleased.setInterpolator(materialInterp);
        return FloatingActionButtonReleased;
    }

    public static ScaleTransition newFloatingActionButtonRestoredAnimation(Node node) {
        ScaleTransition FloatingActionButtonClicked = new ScaleTransition(Duration.millis(250), node);

        FloatingActionButtonClicked.setToX(1);
        FloatingActionButtonClicked.setToY(1);
        FloatingActionButtonClicked.setCycleCount(1);
        FloatingActionButtonClicked.setInterpolator(materialInterp);
        return FloatingActionButtonClicked;
    }

    public static ScaleTransition newFloatingActionButtonClickedAnimation(Node node) {
        ScaleTransition FloatingActionButtonClicked = new ScaleTransition(Duration.millis(250), node);

        FloatingActionButtonClicked.setToX(0);
        FloatingActionButtonClicked.setToY(0);
        FloatingActionButtonClicked.setCycleCount(1);
        FloatingActionButtonClicked.setInterpolator(materialInterp);
        return FloatingActionButtonClicked;
    }


    public static ParallelTransition newCardAnimation(Node node) {
        ParallelTransition NewCard = new ParallelTransition(node);

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
        return NewCard;
    }

    public static ParallelTransition newCardDestroyAnimation(Node node) {
        ParallelTransition NewCardDestroy = new ParallelTransition(node);

        ScaleTransition cardScale = new ScaleTransition(Duration.millis(700));
        TranslateTransition cardTranslate = new TranslateTransition(Duration.millis(700));

        cardTranslate.setByX(180);
        cardTranslate.setByY(180);
        cardTranslate.setCycleCount(1);
        cardTranslate.setInterpolator(materialInterp);

        cardScale.setInterpolator(materialInterp);
        cardScale.setToX(0);
        cardScale.setToY(0);
        cardScale.setCycleCount(1);

        NewCardDestroy.getChildren().addAll(cardScale, cardTranslate);
        return NewCardDestroy;
    }

    public static RotateTransition newSortIconClickedAnimation(Node node, boolean up) {
        RotateTransition SortIconClicked = new RotateTransition(Duration.millis(700), node);
        SortIconClicked.setInterpolator(Interpolator.SPLINE(.62, .28, .23, .99));
        if (up) {
            if (node.getRotate() > 90 && node.getRotate() < 270) SortIconClicked.setToAngle(360);
            else SortIconClicked.setToAngle(0);
            SortIconClicked.setOnFinished((e)->node.setRotate(0));
        } else {
            SortIconClicked.setToAngle(180);
        }
        System.out.println(node.getRotate());
        SortIconClicked.setCycleCount(1);
        return SortIconClicked;
    }
}
