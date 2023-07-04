package com.policedb.policedb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try{
            URL url = new File("src/main/resources/fxml/first_page.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene first_page = new Scene(root);
            stage.setScene(first_page);
            stage.show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
