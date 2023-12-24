package stas.thermometer.infrastructures.database.dbexceptions;

public class DBConnectException extends Exception{

    public DBConnectException() {
        super(" unable to connect to the database");
    }

    public DBConnectException(Exception e) {
        super(" unable to connect to the database", e);
    }
}
