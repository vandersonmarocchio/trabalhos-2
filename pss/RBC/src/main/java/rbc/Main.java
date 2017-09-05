package rbc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rbc.infraestrutura.DataController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("./Interface/Principal.fxml"));
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.setTitle("Principal");
        primaryStage.setScene(new Scene(root,   803, 600));
        primaryStage.show();
    }



    public static void main(final String[] args) throws Exception {
        launch(args);
        DataController.getSession().close();
    }
}
