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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

/**
 * Created by Nick on 12/8/2015.
 */
public class TitleCell extends RudeCell{
    public RudeSortButton mainText;

    public JFXComboBox<String> subMenu;
    public RudeSortButton subMenuButton;
    public MaterialDesignIconView sort2;

    private ObjectProperty<RudeSortButton> activeButton;

    public TitleCell(String mainText, String mainSortProperty, Set<String> properties) {
        super();
        //this.setExempt(true);
        this.setMinHeight(14);

        this.mainText = new RudeSortButton(true);
        this.mainText.setText(mainText);
        this.mainText.setSortProperty(mainSortProperty);
        HBox.setMargin(this.mainText, new Insets(0,0,0,-13));

        this.setHgrow(this.mainText, Priority.ALWAYS);
        this.activeButton = new SimpleObjectProperty<>();
        this.getChildren().add(this.mainText);

        this.subMenuButton = new RudeSortButton(false);
        this.getChildren().add(subMenuButton);

        subMenu = new JFXComboBox<>();
        subMenu.getItems().addAll(properties);
        subMenu.getItems().sort(String::compareToIgnoreCase);

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
