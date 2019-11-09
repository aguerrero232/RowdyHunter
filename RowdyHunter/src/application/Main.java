package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage tmpstage;
    Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            tmpstage = primaryStage;
            root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
            tmpstage.setTitle("ROWDY HUNTER");
            tmpstage.setScene(new Scene(root, 900, 600));
            tmpstage.show();
            tmpstage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}