package stas.thermometer.presentations.messages;

public enum MsgAlert {

    TEMPERATURE("temperature", "--TROP FROID--", "--TROP CHAUD--"),
    HUMIDITY("humidity", "--TROP SEC--", "--TROP HUMIDE--"),
    ERROR_ALERT_TYPE("ERROR", "ERROR ALERT TYPE", "ERROR ALERT TYPE");

    private final String type;
    private final String messagedown;
    private final String messageup;

    MsgAlert(String type, String messagedown, String messageup) {
        this.type = type;
        this.messagedown = messagedown;
        this.messageup = messageup;
    }

    public String getType() {
        return type;
    }

    public String getMessage(int alertType) {
        return switch ( alertType ) {
            case -1 -> this.messagedown;
            case 1 -> this.messageup;
            default -> "ERROR ALERT TYPE";
        };
    }

    public String getMessage() {
        return this.messagedown;
    }

}































/*
public class MsgAlert {
    private final String message;

    public MsgAlert(String type, int alertType) {
        this.message = generateMessage(type, alertType);
    }

    private String generateMessage(String type, int alertType) {
        return switch (type) {
            case "temperature" -> generateTemperatureMessage(alertType);
            case "humidity" -> generateHumidityMessage(alertType);
            default -> "ERROR ALERT TYPE";
        };
    }

    private String generateTemperatureMessage(int alertType) {
        return switch (alertType) {
            case -1 -> "--TROP FROID--";
            case 1 -> "--TROP CHAUD--";
            default -> "ERROR ALERT TYPE";
        };
    }

    private String generateHumidityMessage(int alertType) {
        return switch (alertType) {
            case -1 -> "--TROP SEC--";
            case 1 -> "--TROP HUMIDE--";
            default -> "ERROR ALERT TYPE";
        };
    }

    public String getMessage() {
        return this.message + "\n";
    }
}


 */