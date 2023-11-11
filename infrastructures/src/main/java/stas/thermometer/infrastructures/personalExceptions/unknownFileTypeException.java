package stas.thermometer.infrastructures.personalExceptions;

public class unknownFileTypeException extends Exception {

    private static final long serialVersionUID = 1L;

    public unknownFileTypeException() {
        super(" stas: le type du fichier n'est pas connu");
    }
}