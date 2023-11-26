package stas.thermometer.domains;

import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import java.util.List;

public interface ThermometerInterface {

    String getThermometerName();

    void notifyUpdate();

    List<AggregatorAccessor> getAggregatorsAccessor();
}
