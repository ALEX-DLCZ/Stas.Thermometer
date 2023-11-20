package stas.thermometer.infrastructures.personalExceptions;

public class FileNotFoundException extends Exception {

    public FileNotFoundException() {
        super("stas: configuration file not found");
    }
}
