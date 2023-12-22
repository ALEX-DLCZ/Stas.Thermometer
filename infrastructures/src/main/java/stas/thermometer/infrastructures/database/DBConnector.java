package stas.thermometer.infrastructures.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnector {

    private Connection connection;

    public Connection connect(String connectionString) throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(connectionString);
        }
        return connection;
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Méthode pour récupérer la connexion (éventuellement avec gestion de pool de connexions)
    public Connection getConnection(String connectionString) throws SQLException {
        if (connection == null || connection.isClosed()) {
            return connect(connectionString);
        }
        return connection;
    }

    public int executeInsertion(Connection connection, String insertQuery) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.executeUpdate(); // Exécution de la requête


            // Récupération de la clé générée
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Renvoie la clé générée
                } else {
                    throw new SQLException("Aucune clé générée après l'insertion.");
                }
            }
        }
    }




}
