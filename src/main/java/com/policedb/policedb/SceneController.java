package com.policedb.policedb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

public class SceneController
{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    public static String pub_username_chief;
    public static String pub_username_agent;


    @FXML
    public void chooseChiefLogin(ActionEvent event) throws IOException
    {
        URL url = new File("src/main/resources/fxml/loginChief.fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void chooseAgentLogin(ActionEvent event) throws IOException
    {
        URL url = new File("src/main/resources/fxml/loginAgent.fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void backButtonLogin(ActionEvent event) throws IOException
    {
        URL url = new File("src/main/resources/fxml/first_page.fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void ChiefLogin(ActionEvent event) throws SQLException, IOException{

        Window owner = loginButton.getScene().getWindow();

        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());

        if (usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,  "ERROR!",
                    "Please enter your username");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,  "ERROR!",
                    "Please enter a password");
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        DB_Connection connection = new DB_Connection();
        boolean flag = connection.validate_chief(username, password);

        if (!flag) {
            infoBox("Please enter correct Username and Password", null, "Failed");
        } else {
            infoBox("Login Successful!", null, "Successful");

            pub_username_chief = usernameField.getText();

            URL url = new File("src/main/resources/fxml/panel_chief.fxml").toURI().toURL();
            root = FXMLLoader.load(url);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }

    }

    public void AgentLogin(ActionEvent event) throws SQLException, IOException{
        Window owner = loginButton.getScene().getWindow();

        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());

        if (usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,  "ERROR!",
                    "Please enter your username");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,  "ERROR!",
                    "Please enter a password");
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        DB_Connection connection = new DB_Connection();
        boolean flag = connection.validate_agent(username, password);

        if (!flag) {
            infoBox("Please enter correct Username and Password", null, "Failed");
        } else {
            infoBox("Login Successful!", null, "Failed");

            pub_username_agent = usernameField.getText();

            URL url = new File("src/main/resources/fxml/panel_agent.fxml").toURI().toURL();
            root = FXMLLoader.load(url);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }

    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.show();
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }



    }







