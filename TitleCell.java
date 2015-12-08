package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.sun.org.apache.xerces.internal.parsers.CachingParserPool;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;

/**
 * Created by Nick on 12/8/2015.
 */
public class TitleCell  extends RudeCell{
    public JFXButton mainText;
    public JFXRippler mainTextRippler;

    public TitleCell(String mainText) {
        super();
        this.mainText = new JFXButton(mainText);
        this.setHgrow(this.mainText, Priority.ALWAYS);
        this.mainText.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.mainText.getStyleClass().add("button-title");


        this.mainTextRippler = new JFXRippler(this.mainText);
        mainTextRippler.setRipplerFill(Paint.valueOf("#FF0000"));
        this.getChildren().add(mainTextRippler);



        this.getChildren().add(this.mainText);
    }

    @Override
    public void handleMouseEntered(MouseEvent event) {
        System.out.println("function");
        super.handleMouseEntered(event);
        this.mainText.fire();
    }
}
