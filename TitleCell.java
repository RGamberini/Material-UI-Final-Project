package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRippler;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.RotateTransition;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import jdk.nashorn.internal.ir.annotations.Ignore;

/**
 * Created by Nick on 12/8/2015.
 */
public class TitleCell extends RudeCell{
    public RudeSortButton mainText;

    public JFXComboBox<String> subMenu;
    public RudeSortButton subMenuButton;
    public MaterialDesignIconView sort2;

    private ObjectProperty<RudeSortButton> activeButton;

    public TitleCell(String mainText) {
        super();
        //this.setExempt(true);

        this.mainText = new RudeSortButton(true);
        this.mainText.setText(mainText);
        this.mainText.setSortProperty(Person.properties.get(Person.properties.indexOf("Last Name")));

        this.setHgrow(this.mainText, Priority.ALWAYS);
        this.activeButton = new SimpleObjectProperty<>();
        this.getChildren().add(this.mainText);

        this.subMenuButton = new RudeSortButton(false);
        this.getChildren().add(subMenuButton);

        subMenu = new JFXComboBox<>();
        subMenu.getItems().addAll(Person.properties);

        this.setMargin(subMenu, new Insets(0, 10, 0, 0));
        this.getChildren().add(subMenu);

        //THE LISTENERS
        this.mainText.activeProperty().addListener((o, oldVal, newVal) -> {
            if (newVal) {
                setActiveButton(this.mainText);
                subMenuButton.setActive(false);
            }
        });

        this.subMenuButton.activeProperty().addListener((o, oldVal, newVal) -> {
            if (newVal) {
                setActiveButton(subMenuButton);
                this.mainText.setActive(false);
            }
        });

        this.subMenu.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            System.out.println(newVal);
            this.subMenuButton.setSortProperty(newVal);
            this.subMenuButton.setActive(true);
        });
    }

    private JFXButton getActiveButton() {
        return activeButton.get();
    }

    public ObjectProperty<RudeSortButton> activeButtonProperty() {
        return activeButton;
    }

    private void setActiveButton(RudeSortButton activeButton) {
        this.activeButton.set(activeButton);
    }
}
