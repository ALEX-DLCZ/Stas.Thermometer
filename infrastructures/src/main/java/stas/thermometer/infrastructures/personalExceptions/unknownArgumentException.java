package stas.thermometer.infrastructures.personalExceptions;

public class unknownArgumentException extends Exception {


    public unknownArgumentException() {
        super("stas: configuration file not found");
    }
}
