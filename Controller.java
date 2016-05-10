package sample;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Controller {
    @FXML public StackPane mainContainer = new StackPane();
    @FXML public JFXTabPane tabPane = new JFXTabPane();
    @FXML public Tab students = new Tab(), faculty = new Tab(), textbooks = new Tab(), courses = new Tab();
    private Map<Class<? extends RudeObject>, ArrayList<RudeObject>> randomSet;

    @FXML
    public void initialize() throws IOException {
        //The errror box
        RudeErrorPane errorPane = new RudeErrorPane();
        JFXDialog dialog =  new JFXDialog(mainContainer, errorPane, JFXDialog.DialogTransition.CENTER);
        errorPane.button.setOnMouseClicked((_e) -> dialog.close());

        randomSet = RandomRudeObjectFactory.randomSet(20);

        students.setContent(newTabPane(dialog, Student.class, randomSet, students));
        textbooks.setContent(newTabPane(dialog, Textbook.class, randomSet, textbooks));
        courses.setContent(newTabPane(dialog, Course.class, randomSet, courses));
        faculty.setContent(newTabPane(dialog, Faculty.class, randomSet, faculty));
    }

    public HBox newTabPane(JFXDialog dialog, Class<? extends RudeObject> cls, Map<Class<? extends RudeObject>, ArrayList<RudeObject>> randomSet, Tab tab) throws IOException {
        HBox mainHbox = new HBox();
        mainHbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        MainListCard mainListCard = new MainListCard(dialog, cls, randomSet);
        mainHbox.getChildren().add(new VBox(mainListCard));

        //Profile card instantiation
        ProfileCardReusable profileCard = new ProfileCardReusable();
        mainHbox.getChildren().add(profileCard);


        mainListCard.myListView.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (mainListCard.listViewData.contains(newVal)) {
                profileCard.setMainRudeObject(newVal);
            }
        });

        //Fire these events so it all looks right to the user
//        mainListCard.titleCell.subMenu.getSelectionModel().select(0);
        mainListCard.titleCell.mainText.setActive(true);
        mainListCard.myListView.getSelectionModel().select(0);
        tab.setOnSelectionChanged((e) ->  {
            mainListCard.myListView.setItems(null);
            mainListCard.myListView.setItems(mainListCard.listViewData);
        });

        return mainHbox;
    }
}
