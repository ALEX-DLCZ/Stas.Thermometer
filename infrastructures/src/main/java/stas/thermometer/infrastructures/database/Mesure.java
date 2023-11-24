package stas.thermometer.infrastructures.database;

import java.time.LocalDateTime;

public class Mesure {

    private String thermometerName;
    private LocalDateTime datetime;
    private String type;
    private String format;
    private double value;

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
    public void setThermometerName(String thermometerName) {
        this.thermometerName = thermometerName;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }

    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }

}
