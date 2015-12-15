package sample;

import com.jfoenix.controls.JFXListView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent) loader.load();

        Scene scene = new Scene(root, 1000, 500);
//        scene.getStylesheets().add(getClass().getResource("material-fx-v0_3.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        //scene.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> System.out.println("meme1"));

        primaryStage.setTitle("Educational Manager V.2");
        primaryStage.setScene(scene);
        //primaryStage.setResizable(false);

        Controller controller = loader.getController();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
