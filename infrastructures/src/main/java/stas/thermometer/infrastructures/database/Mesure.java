package stas.thermometer.infrastructures.database;

import java.time.LocalDateTime;

public class Mesure {

    private final String thermometerName;
    private final LocalDateTime datetime;
    private final String type;
    private final String format;
    private final double value;


    public Mesure(String thermometerName, LocalDateTime datetime, String type, String format, double value) {
        this.thermometerName = thermometerName;
        this.datetime = datetime;
        this.type = type;
        this.format = format;
        this.value = value;
    }

    //--------------------------

    public String getThermometerName() {
        return thermometerName;
    }
    public LocalDateTime getDatetime() {
        return datetime;
    }
    public String getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }
    public double getValue() {
        return value;
    }

}
