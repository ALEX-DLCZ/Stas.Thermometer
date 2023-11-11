package stas.thermometer.domains.personalExceptions;

public class NameNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public NameNotFoundException() {
        super("stas: missing required property name");
    }
}
