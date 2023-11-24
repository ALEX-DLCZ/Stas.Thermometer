package stas.thermometer.infrastructures.database.dbexceptions;

public class DBInsertException extends Exception{

    public DBInsertException() {
        super(" unable to insert data");
    }
}
