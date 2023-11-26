package stas.thermometer.domains.personal.exceptions;

public class PropertyNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public PropertyNotFoundException() {
        super("missing required properties");
    }
}
