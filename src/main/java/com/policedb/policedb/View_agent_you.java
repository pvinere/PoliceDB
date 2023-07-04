package com.policedb.policedb;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class View_agent_you implements Initializable{

    private static final String SELECT_VIEW = "SELECT * from agents where boss_name = ?";

    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/policedb";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "1234";

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableView<Agents> tableView;
    @FXML
    private TableColumn<Agents, Integer> col_id;
    @FXML
    private TableColumn<Agents, String> col_username;
    @FXML
    private TableColumn<Agents, String> col_password;
    @FXML
    private TableColumn<Agents, String> col_full_name;
    @FXML
    private TableColumn<Agents, String> col_boss_name;

    ObservableList<Agents> listM;


    public static ObservableList<Agents> getDataAgents() throws SQLException {

        ObservableList<Agents> list = FXCollections.observableArrayList();

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement state = connection.prepareStatement(SELECT_VIEW)) {
            state.setString(1, chiefPanel.getChiefName(SceneController.pub_username_chief));
            System.out.println(state);
            ResultSet rs = state.executeQuery();


            while (rs.next()) {

                list.add(new Agents(Integer.parseInt(rs.getString("agent_id")),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("boss_name")));
            }
        }
        return list;
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_id.setCellValueFactory(new PropertyValueFactory<Agents,Integer>("agent_id"));
        col_username.setCellValueFactory(new PropertyValueFactory<Agents,String>("username"));
        col_password.setCellValueFactory(new PropertyValueFactory<Agents,String>("password"));
        col_full_name.setCellValueFactory(new PropertyValueFactory<Agents,String>("full_name"));
        col_boss_name.setCellValueFactory(new PropertyValueFactory<Agents,String>("boss_name"));

        try {
            listM = getDataAgents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableView.setItems(listM);
    }

}
