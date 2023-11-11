package stas.thermometer.presentations.messages;

import stas.thermometer.domains.MeasurementType;

public class MsgAlert {

    private final String message;

    public MsgAlert(MeasurementType type, int alertType) {

        switch (type.getType()) {
            case "temperature":
                switch (alertType) {
                    case 1:
                        this.message = "--TROP FROID--";
                        break;
                    case 2:
                        this.message = "--TROP CHAUD--";
                        break;
                    default:
                        this.message = "ERROR ALERT TYPE";
                        break;
                }
                break;
            case "humidity":
                switch (alertType) {
                    case 1:
                        this.message = "--TROP SEC--";
                        break;
                    case 2:
                        this.message = "--TROP HUMIDE--";
                        break;
                    default:
                        this.message = "ERROR ALERT TYPE";
                        break;
                }
                break;
            default:
                this.message = "ERROR ALERT TYPE";
                break;
        }
    }

    public String getMessage() {
        return this.message + "\n";
    }
}
