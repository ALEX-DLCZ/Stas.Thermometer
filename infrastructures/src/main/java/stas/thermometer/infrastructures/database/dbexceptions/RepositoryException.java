package stas.thermometer.infrastructures.database.dbexceptions;

public class RepositoryException extends Exception {
    public RepositoryException(String message, Throwable e) {
        super(message, e);
    }
}