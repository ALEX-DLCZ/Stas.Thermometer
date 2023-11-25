package stas.thermometer.presentations.messages;


public enum MsgAlert {
    TEMPERATURE_COLD("--TROP FROID--"),
    TEMPERATURE_HOT("--TROP CHAUD--"),
    HUMIDITY_DRY("--TROP SEC--"),
    HUMIDITY_WET("--TROP HUMIDE--"),
    ERROR_ALERT_TYPE("ERROR ALERT TYPE");

    private final String message;

    MsgAlert(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message + "\n";
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