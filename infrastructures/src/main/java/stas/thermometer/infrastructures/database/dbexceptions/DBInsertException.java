package stas.thermometer.infrastructures.database.dbexceptions;

import java.sql.SQLException;

public class DBInsertException extends Exception{

    public DBInsertException() {
        super(" unable to insert data");
    }


    public DBInsertException(SQLException e) {
        super(" unable to insert data", e);
    }
    public DBInsertException(Exception e) {
        super(" unable to insert data" + e.toString());
    }
}
