package org.example.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(String databaseUrl) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (connection.isClosed()) {
//                connection.;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

//    public void closeConnection() {
//        try {
//            if (connection != null && !connection.isClosed()) {
//                connection.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
