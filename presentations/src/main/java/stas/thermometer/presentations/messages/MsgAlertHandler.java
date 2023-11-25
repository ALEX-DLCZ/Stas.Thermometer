package stas.thermometer.presentations.messages;

import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

class MsgAlertHandler implements MessageGenerator {

    @Override
    public String getMessage(AggregatorAccessor aggregatorAccessorCible) {
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

    // Autres méthodes spécifiques à la gestion des messages d'alerte
}