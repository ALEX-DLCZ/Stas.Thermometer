package stas.thermometer.domains;

public enum MeasurementType {

    TEMPERATURE("temperature"),
    HUMIDITY("humidity");

    public final String type;

    MeasurementType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}
