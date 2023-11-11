package stas.thermometer.domains.personalExceptions;

public class PropertyNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public PropertyNotFoundException() {
        super("stas: missing required properties");
    }
}
