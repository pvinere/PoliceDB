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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

import static com.policedb.policedb.SceneController.showAlert;

public class removeAgentPanel {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/policedb";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "1234";

    private static final String DELETE_AGENT_BY_USERNAME = "DELETE FROM agents WHERE username = ?";
    private static final String DELETE_AGENT_BY_FULLNAME = "DELETE FROM agents WHERE full_name = ?";
    private static final String DELETE_AGENT_BY_ID = "DELETE FROM agents WHERE agent_id = ?";
    private static final String SEARCH_USERNAME = "SELECT * FROM agents WHERE username = ?";
    private static final String SEARCH_FULLNAME = "SELECT * FROM agents WHERE full_name = ?";
    private static final String SEARCH_ID = "SELECT * FROM agents WHERE agent_id = ?";

    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    private TextField full_name_agent;
    @FXML
    private TextField username_agent;
    @FXML
    private TextField id_agent;

    private boolean getConnection(String search, String selectQuery) {
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             // Step 2:Create a statement using connection object
             PreparedStatement state = connection.prepareStatement(selectQuery)) {
            state.setString(1, search);


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

    private boolean searchID(String id, String selectQuery) {
        return getConnection(id, selectQuery);

    }

    private boolean searchUsername(String username, String selectQuery) {
        return getConnection(username, selectQuery);

    }

    private boolean searchFullName(String full_name, String selectQuery) {
        return getConnection(full_name, selectQuery);

    }


    @FXML
    public void deleteAgentInDatabase(ActionEvent event) throws SQLException {
//        System.out.println(full_name_agent.getText());
//        System.out.println(username_agent.getText());
//        System.out.println(id_agent.getText());

        if (username_agent.getText().isEmpty() && id_agent.getText().isEmpty() && full_name_agent.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "ERROR!",
                    "Please enter the Agent username or full name or id");
            return;
        }
        String username = username_agent.getText();
        String full_name = full_name_agent.getText();
        String id = id_agent.getText();
        boolean flag_username = searchUsername(username, SEARCH_USERNAME);
        boolean flag_fullname = searchFullName(full_name,SEARCH_FULLNAME);
        boolean flag_id = searchID(id,SEARCH_ID);

        if(id_agent.getText().isEmpty() && full_name_agent.getText().isEmpty())
        {
            if (!flag_username) {
                SceneController.infoBox("Please enter correct Username", null, "Failed");
                return;
            } else {
                try (Connection connection = DriverManager
                        .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                     PreparedStatement dl = connection.prepareStatement(DELETE_AGENT_BY_USERNAME)) {
                    dl.setString(1, username);
                    dl.executeUpdate();
                    SceneController.infoBox("Agent with username: " + username_agent.getText() + " was DELETED!", null, "Failed");
                }
            }
        }

        if(username_agent.getText().isEmpty() && id_agent.getText().isEmpty())
        {
            if (!flag_fullname) {
                SceneController.infoBox("Please enter correct Full Name", null, "Failed");
                return;
            }else {
                try (Connection connection = DriverManager
                        .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                     PreparedStatement dl = connection.prepareStatement(DELETE_AGENT_BY_FULLNAME)) {
                    dl.setString(1, full_name);
                    dl.executeUpdate();
                    SceneController.infoBox("Agent with full name : " + full_name_agent.getText() + " was DELETED!", null, "Failed");
                }
            }
        }

        if(username_agent.getText().isEmpty() && full_name_agent.getText().isEmpty())
        {
            if(!flag_id) {
                SceneController.infoBox("Please enter correct ID", null, "Failed");
                return;
            }
            else {
                try (Connection connection = DriverManager
                        .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                     PreparedStatement dl = connection.prepareStatement(DELETE_AGENT_BY_ID)) {
                    dl.setString(1, id);
                    dl.executeUpdate();
                    SceneController.infoBox("Agent with id : " + id_agent.getText() + " was DELETED!", null, "Failed");
                }
            }

        }

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
}
