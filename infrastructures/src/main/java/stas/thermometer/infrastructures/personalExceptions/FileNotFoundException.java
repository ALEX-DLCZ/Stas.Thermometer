package stas.thermometer.infrastructures.personalExceptions;

public class FileNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public FileNotFoundException() {
        super("stas: configuration file not found");
    }
}
