package stas.thermometer.infrastructures.personal.exceptions;

public class unknownFileTypeException extends Exception {

    public unknownFileTypeException() {
        super(" stas: le type du fichier n'est pas connu");
    }
}