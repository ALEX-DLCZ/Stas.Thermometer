package stas.thermometer.infrastructures.personal.exceptions;

public class unknownArgumentException extends Exception {

    public unknownArgumentException() {
        super("stas: configuration file not found");
    }
}