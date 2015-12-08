package sample;

import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

/**
 * Created by Nick on 12/6/2015.
 */
public class Student extends ListCell {
    public String firstName, lastName;
    public String gpa;
    @FXML public Label property;

    public Student(String firstName, String lastName, double gpa) {
        this(firstName, lastName, Double.toString(gpa));
    }

    public Student(String firstName, String lastName, String gpa) {
        super(firstName + " " + lastName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.gpa = gpa;

        this.property = new Label(gpa);
        this.property.setPadding(new Insets(0, 0, 0, 30));
        this.getChildren().add(property);
        System.out.println(this.getStyleClass());
    }
}
