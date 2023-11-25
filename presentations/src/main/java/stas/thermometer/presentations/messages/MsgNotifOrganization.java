package stas.thermometer.presentations.messages;

import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import java.util.Map;


/**
 * @implNote Cette classe permet de générer les messages de notification à partir des données de l'aggrégateur
 *
 */
public class MsgNotifOrganization {
    private final MessageGenerator currentHandler;
    private final MessageGenerator alertHandler;

    public MsgNotifOrganization(Map<String, String> format) {
        this.currentHandler = new MsgCurrentHandler(format);
        this.alertHandler = new MsgAlertHandler();
    }

    public String getMsgCurrent(AggregatorAccessor aggregatorAccessorCible) {
        return currentHandler.getMessage(aggregatorAccessorCible);
    }

    public String getMsgAlert(AggregatorAccessor aggregatorAccessorCible) {
        return alertHandler.getMessage(aggregatorAccessorCible) + "\n" + currentHandler.getMessage(aggregatorAccessorCible);

    }
}

/*

public class MsgNotifOrganization {
    private final Map<String, String> format;


    public MsgNotifOrganization(Map<String, String> format) {
        this.format = format;

    }


    public String getMsgCurrent(AggregatorAccessor aggregatorAccessorCible) {


        try {
            for (MsgCurrent msgCurrent : MsgCurrent.values()) {
                if (msgCurrent.getType().equals(aggregatorAccessorCible.getName())) {
                    return msgCurrent.getMessage(
                            aggregatorAccessorCible.getmesurementMod().dateTime(),
                            aggregatorAccessorCible.getmesurementMod().value(),
                            format.get("datetime"),
                            format.get(msgCurrent.getType())
                    );
                }
            }
        } catch (Exception e) {
            return MsgCurrent.ERROR_CURRENT_TYPE.getMessage();
        }
        return MsgCurrent.ERROR_CURRENT_TYPE.getMessage();
    }


    public String getMsgAlert(AggregatorAccessor aggregatorAccessorCible) {
        try {
            for (MsgAlert msgAlert : MsgAlert.values()) {
                if (msgAlert.getType().equals(aggregatorAccessorCible.getName())) {
                    return msgAlert.getMessage(aggregatorAccessorCible.getAlarmType());
                }
            }
        } catch (Exception e) {
            return MsgAlert.ERROR_ALERT_TYPE.getMessage();
        }
        return MsgAlert.ERROR_ALERT_TYPE.getMessage();
    }
}
*/
