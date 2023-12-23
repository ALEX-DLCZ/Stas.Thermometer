package stas.thermometer.infrastructures.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnector {
    private Connection connection;
    private String connectionString;


    public DBConnector(String connectionString) {
        this.connectionString = connectionString;
    }

    public Connection connect(String connectionString) throws SQLException {
        if (connection == null || connection.isClosed()) {
            this.connectionString = connectionString;
            connection = DriverManager.getConnection(connectionString);
        }
        return connection;
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            return connect(connectionString);
        }
        return connection;
    }
}
