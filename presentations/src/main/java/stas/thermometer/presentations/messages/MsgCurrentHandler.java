package stas.thermometer.presentations.messages;

import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import java.util.Map;

public class MsgCurrentHandler implements MessageGenerator {
    private final Map<String, String> format;

    public MsgCurrentHandler(Map<String, String> format) {
        this.format = format;
    }

    @Override
    public String getMessage(AggregatorAccessor aggregatorAccessorCible) {

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
}