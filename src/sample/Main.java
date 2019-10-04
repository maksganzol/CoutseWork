package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


//1.1, 2.6, 3.6, 4.4, 5.3
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/sample.fxml"));
        primaryStage.setTitle("Course work Tsurkan Maxim AS-181");
        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
