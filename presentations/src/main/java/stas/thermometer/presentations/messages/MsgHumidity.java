package stas.thermometer.presentations.messages;

import java.time.LocalDateTime;

public class MsgHumidity {


    private final String message;

    public MsgHumidity(LocalDateTime dateTime, double humidity, String formatDateTime, String unit) {
        // exemple de message: "16/11/2023 14:52:24 : Humidité moyenne des 2 dernières seconde : 23%"
        // exemple de temperature:"0,2315802" formatDateTime: "dd/MM/YYYY à HH :mm :ss" exemple de unit: "0%"
        this.message = dateTime.format(java.time.format.DateTimeFormatter.ofPattern(formatDateTime)) + " : Humidité moyenne des 2 dernières seconde : " + String.format(unit, humidity);
    }

    public String getMessage() {
        return this.message;
    }
}
