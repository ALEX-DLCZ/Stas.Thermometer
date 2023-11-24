package stas.thermometer.infrastructures.database.dbexceptions;

public class DBConnectException extends Exception{

    public DBConnectException() {
        super(" unable to connect to the database");
    }
}
