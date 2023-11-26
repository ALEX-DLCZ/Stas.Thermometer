package stas.thermometer.presentations.messages;

import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

public class MsgAlertHandler implements MessageGenerator {

    @Override
    public String getMessage(AggregatorAccessor aggregatorAccessorCible) {
        for ( MsgAlert msgAlert : MsgAlert.values() ) {
            if ( msgAlert.getType().equals(aggregatorAccessorCible.getName()) ) {
                return msgAlert.getMessage(aggregatorAccessorCible.getAlarmType());
            }
        }

        return MsgAlert.ERROR_ALERT_TYPE.getMessage();
    }

}