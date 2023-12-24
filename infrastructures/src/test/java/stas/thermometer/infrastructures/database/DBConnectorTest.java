package stas.thermometer.infrastructures.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectorTest {

    private static final String connectionString = "jdbc:derby:../StasThermometerDerby";

    @Test
    public void Should_Return_Connection_When_Connect() {
        DBConnector dbConnector = new DBConnector(connectionString);
        try ( Connection connection = dbConnector.getConnection() ) {
            assertNotNull(connection);
        } catch ( SQLException e ) {
            fail("Connection failed");
        }
    }

    @Test
    public void Should_Return_Same_Connection_When_getConnection_After_Connect() {
        DBConnector dbConnector = new DBConnector(connectionString);
        try ( Connection connection = dbConnector.getConnection() ) {
            try ( Connection connection2 = dbConnector.getConnection() ) {
                assertEquals(connection, connection2);
            } catch ( SQLException e ) {
                fail("Connection failed");

            }
        } catch ( SQLException e ) {
            fail("Connection failed");
        }
    }

    @Test
    public void Should_Return_New_Connection_When_getConnection_After_Disconnect() {
        DBConnector dbConnector = new DBConnector(connectionString);
        try ( Connection connection = dbConnector.getConnection() ) {
            dbConnector.disconnect();
            try ( Connection connection2 = dbConnector.getConnection() ) {
                assertNotEquals(connection, connection2);
            } catch ( SQLException e ) {
                fail("Connection failed");

            }
        } catch ( SQLException e ) {
            fail("Connection failed");
        }
    }

    //test le cas ou la connection est fermée
    @Test
    public void Should_Return_New_Connection_When_getConnection_After_Close() {
        DBConnector dbConnector = new DBConnector(connectionString);
        try ( Connection connection = dbConnector.getConnection() ) {
            connection.close();
            try ( Connection connection2 = dbConnector.getConnection() ) {
                assertNotEquals(connection, connection2);
            } catch ( SQLException e ) {
                fail("Connection failed");

            }
        } catch ( SQLException e ) {
            fail("Connection failed");
        }
    }

    @Test
    public void Should_Return_New_Connection_When_getConnection_After_Close_And_Disconnect() {
        DBConnector dbConnector = new DBConnector(connectionString);
        try ( Connection connection = dbConnector.getConnection() ) {
            connection.close();
            dbConnector.disconnect();
            try ( Connection connection2 = dbConnector.getConnection() ) {
                assertNotEquals(connection, connection2);
            } catch ( SQLException e ) {
                fail("Connection failed");

            }
        } catch ( SQLException e ) {
            fail("Connection failed");
        }
    }
}
