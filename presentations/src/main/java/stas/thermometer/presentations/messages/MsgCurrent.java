package stas.thermometer.presentations.messages;


import java.text.DecimalFormat;
import java.time.LocalDateTime;

public enum MsgCurrent {
    TEMPERATURE("temperature","Température actuelle : "),
    HUMIDITY("humidity","Humidité actuelle : "),
    ERROR_CURRENT_TYPE("ERROR", "ERROR CURRENT TYPE");

    private final String message;
    private final String type;

    MsgCurrent(String type, String message) {
        this.type = type;
        this.message = message;

    }

    public String getType() {
        return type;
    }

    public String getMessage(LocalDateTime dateTime, double value, String formatDateTime, String unit) {

        // exemple de message: "16/11/2023 14:52:24 : Humidité moyenne des 2 dernières seconde : 23%"
        // exemple de temperature:"0,2315802" formatDateTime: "dd/MM/YYYY à HH :mm :ss" exemple de unit: "0%"
        // ou
        // exemple de message: 11/11/2023 12:12:12 : Température moyenne des 2 dernières seconde : 12.30°
        // exemple de temperature:"12,3028943" formatDateTime: "dd/MM/YYYY à HH :mm :ss" exemple de unit: "00.00°"

        DecimalFormat df = new DecimalFormat(unit);
        String valueString = df.format(value);
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern(formatDateTime)) + " : " + this.message + valueString + "\n";
    }

    public String getMessage() {
        return this.message;
    }
}


















/*
public class MsgCurrent {


    private final String message;

    public MsgCurrent() {

        this.message = "";
    }

    public String getMessage() {
        return this.message;
    }
}

 */
