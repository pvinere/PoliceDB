package com.policedb.policedb;

import java.sql.*;

public class DB_Connection {
    // Replace below database url, username and password with your actual database credentials
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/policedb";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "1234";
    private static final String SELECT_QUERY_CHIEF = "SELECT * FROM chiefs WHERE username = ? and password = ?";
    private static final String SELECT_QUERY_AGENT = "SELECT * FROM agents WHERE username = ? and password = ?";


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
            printSQLException(e);
        }
        return false;
    }

    public boolean validate_agent(String username, String password) {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        return getConnection(username, password, SELECT_QUERY_AGENT);
    }

    public boolean validate_chief(String username, String password) {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        return getConnection(username, password, SELECT_QUERY_CHIEF);
    }



    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
