package com.policedb.policedb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.policedb.policedb.SceneController.showAlert;

public class addAgentPanel {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/policedb";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "1234";
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField full_name_agent;
    @FXML
    private TextField username_agent;
    @FXML
    private TextField password_agent;


    private static final String INSERT_AGENT = "INSERT INTO agents (username, password, full_name, boss_name) VALUES (?, ?, ?, ?)";


    @FXML
    public void backButtonChiefPanel(ActionEvent event) throws IOException
    {
        URL url = new File("src/main/resources/fxml/panel_chief.fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void addAgentInDatabase(ActionEvent event) throws SQLException {


        System.out.println(full_name_agent.getText());
        System.out.println(username_agent.getText());
        System.out.println(password_agent.getText());


        if (full_name_agent.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "ERROR!",
                    "Please enter the Agent full name");
            return;
        }
        if (username_agent.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,  "ERROR!",
                    "Please enter the Agent new username");
            return;
        }
        if (password_agent.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "ERROR!",
                    "Please enter the Agent new password");
            return;
        }
        String full_name = full_name_agent.getText();
        String username = username_agent.getText();
        String password = password_agent.getText();


        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

                    PreparedStatement state = connection.prepareStatement(INSERT_AGENT)){
                 state.setString(1,username);
                 state.setString(2,password);
                 state.setString(3,full_name);
                 state.setString(4,chiefPanel.getChiefName(SceneController.pub_username_chief));
                state.executeUpdate();
            SceneController.infoBox("Agent " + full_name + " was addes succesfuly!", null, "Successful");
        }

    }

}
