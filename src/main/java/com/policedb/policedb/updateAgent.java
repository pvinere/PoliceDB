package com.policedb.policedb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

import static com.policedb.policedb.SceneController.showAlert;


public class updateAgent {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/policedb";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "1234";
    private static final String SEARCH_USERNAME_AND_PASSWORD = "SELECT * FROM agents WHERE username = ? and password = ?";
    private static final String UPDATE_USERNAME = "UPDATE agents SET username = ? WHERE username = ?";
    private static final String UPDATE_PASSWORD = "UPDATE agents SET password = ? WHERE password = ?";
    @FXML
    private TextField username_agent;
    @FXML
    private TextField password_agent;

    @FXML
    private TextField update_username;
    @FXML
    private TextField update_password;

    boolean data_verification = false;

    private boolean getConnection(String username, String password, String selectQuery) {
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             // Step 2:Create a statement using connection object
             PreparedStatement state = connection.prepareStatement(selectQuery)) {
            state.setString(1, username);
            state.setString(2, password);

            System.out.println(state);

            ResultSet resultSet = state.executeQuery();
            if (resultSet.next()) {
                return true;
            }


        } catch (SQLException e) {
            // print SQL exception information
            DB_Connection.printSQLException(e);
        }
        return false;
    }

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
    public void checkUsernameAndPassword(ActionEvent event) throws SQLException {
        String username = username_agent.getText();
        String password = password_agent.getText();


        DB_Connection connectionDB = new DB_Connection();
        boolean flag = connectionDB.validate_agent(username, password);

        if (!flag) {
            SceneController.infoBox("Please enter correct Agent Username and Password", null, "Failed");
            data_verification = false;
        } else {
            SceneController.infoBox("Data's are correct, you can now change the username and password", null, "Failed");
            //update la username sau password
            data_verification = true;
        }

    }

    public void updateAgentData(ActionEvent event) throws SQLException {

        String new_username = update_username.getText();
        String new_password = update_password.getText();
        String old_username = username_agent.getText();
        String old_password = password_agent.getText();

        if(data_verification == true)
        {
            if(update_password.getText().isEmpty())
            {
                try (Connection connection = DriverManager
                        .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                     PreparedStatement dl = connection.prepareStatement(UPDATE_USERNAME)) {
                    dl.setString(1, new_username);
                    dl.setString(2, old_username);
                    dl.executeUpdate();
                    SceneController.infoBox("The new agent username is: " + new_username , null, "Failed");
                }
            }
            else if(update_username.getText().isEmpty())
            {
                try (Connection connection = DriverManager
                        .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                     PreparedStatement dl = connection.prepareStatement(UPDATE_PASSWORD)) {
                    dl.setString(1, new_password);
                    dl.setString(2, old_password);
                    dl.executeUpdate();
                    SceneController.infoBox("The new agent password is: " + new_password , null, "Failed");
                }
            }
            else
            {
                try (Connection connection = DriverManager
                        .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                     PreparedStatement dl = connection.prepareStatement(UPDATE_USERNAME)) {
                    dl.setString(1, new_username);
                    dl.setString(2, old_username);
                    dl.executeUpdate();

                }

                try (Connection connection = DriverManager
                        .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                     PreparedStatement dl = connection.prepareStatement(UPDATE_PASSWORD)) {
                    dl.setString(1, new_password);
                    dl.setString(2, old_password);
                    dl.executeUpdate();
                    SceneController.infoBox("The new agent username and password is: " + new_username + " " + new_password , null, "Failed");
                }
            }
        }
        else {
            SceneController.infoBox("Please enter the old Agent Username and Password", null, "Failed");

        }

    }

    public boolean validate_agent(String username, String password) {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        return getConnection(username, password, SEARCH_USERNAME_AND_PASSWORD);
    }

}
