package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainImgWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("imageChooser.fxml"));
        primaryStage.setTitle("Java 06 ");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();

    }


    public void MainImgWindow(){

    }

    public static void main(String[] args) {
        launch(args);
    }
}
