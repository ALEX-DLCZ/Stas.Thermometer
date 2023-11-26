package stas.thermometer.presentations.messages;

import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

public interface MessageGenerator {

    String getMessage(AggregatorAccessor accessor);

}
