package stas.thermometer.presentations.messages;

import java.time.LocalDateTime;

public class MsgTemperature {


    private final String message;

    public MsgTemperature(LocalDateTime dateTime, double temperature, String formatDateTime, String unit) {
        // exemple de message: 11/11/2023 12:12:12 : Température moyenne des 2 dernières seconde : 12.30°
        // exemple de temperature:"12,3028943" formatDateTime: "dd/MM/YYYY à HH :mm :ss" exemple de unit: "00.00°"
        this.message = dateTime.format(java.time.format.DateTimeFormatter.ofPattern(formatDateTime)) + " : Température moyenne des 2 dernières seconde : " + String.format(unit, temperature);
    }

    public String getMessage() {
        return this.message;
    }
}
