package com.policedb.policedb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class agentPanel implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static final String GET_AGENT_NAME = "SELECT full_name FROM agents WHERE username = ?";

    @FXML
    private Label callName;


    public static String getAgentName(String username) throws SQLException {
        String name = null;
        try (Connection connection = DriverManager
                .getConnection(DB_Connection.DATABASE_URL, DB_Connection.DATABASE_USERNAME, DB_Connection.DATABASE_PASSWORD);
             PreparedStatement state = connection.prepareStatement(GET_AGENT_NAME)) {
            state.setString(1, username);

            System.out.println(state);
            ResultSet rs = state.executeQuery();

            while(rs.next()){
                System.out.println("Name: " + rs.getString("full_name"));
                name = rs.getString("full_name");
            }

        }

        return name;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            callName.setText("Hello Agent " + getAgentName(SceneController.pub_username_agent));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
