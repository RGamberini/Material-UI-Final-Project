package sample;

import com.jfoenix.controls.JFXButton;
import javafx.animation.ParallelTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * Created by Nick on 12/16/2015.
 */
public class RudeErrorPane extends Pane {
    private static final double WIDTH = 267.0, HEIGHT = 200.0;
    public JFXButton button = new JFXButton();

    public RudeErrorPane() {
        this.getStyleClass().add("card");
        this.setPrefSize(WIDTH, HEIGHT);

        VBox vBox = new VBox(14);
        vBox.setPadding(new Insets(20, 24, 0, 24));
        vBox.setPrefSize(WIDTH, HEIGHT);
        vBox.setAlignment(Pos.TOP_RIGHT);

        Label title = new Label("Properties are blank");
        title.setTextFill(Paint.valueOf("#000000"));
        title.setFont(new Font("System", 19));
        title.setStyle("-fx-font-weight: bold;");
        title.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        title.getStyleClass().remove("label");

        Label body = new Label("Some properties of this form have been left blank. Please fill them before continuing.");
        body.setWrapText(true);
        body.setFont(new Font(17));
        body.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setMargin(body, new Insets(-10, 0, 0, 0));

        button.setText("Okay");
        button.getStyleClass().add("button-cancel");
        button.setStyle("-fx-border-style: hidden hidden hidden hidden;");

        this.getChildren().add(vBox);
        vBox.getChildren().addAll(title, body, button);
    }
}
