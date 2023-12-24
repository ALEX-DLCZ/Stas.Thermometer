package stas.thermometer.infrastructures.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.fail;

public class DerbyConnectionTest {

    //jdbc:derby:C:/0alex/HELMO/B2_A.I/PROG/stas.thermometer/StasThermometerDerby;create=true
    private static final String connectionString = "jdbc:derby:../StasThermometerDerby";


    @Test
    public void getDerbyConnection() {


        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch ( ClassNotFoundException e ) {
            fail("Driver not found");
        }

       try ( Connection connection = DriverManager.getConnection(connectionString, "demo", "demo")) {
        } catch ( Exception e ) {
            fail("Connection failed");
        }

    }


}
