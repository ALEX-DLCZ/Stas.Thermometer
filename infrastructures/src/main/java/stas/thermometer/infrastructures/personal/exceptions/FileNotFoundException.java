package stas.thermometer.infrastructures.personal.exceptions;

public class FileNotFoundException extends Exception {

    public FileNotFoundException() {
        super("configuration file not found");
    }
}
