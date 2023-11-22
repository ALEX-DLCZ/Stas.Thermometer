package stas.thermometer.domains;

import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import java.util.List;
import java.util.Map;

public interface ThermometerRepositoryInterface {

    String getThermometerName();

    Map<String, String> getFormat();

    void notifyUpdate();

    List<AggregatorAccessor> getAggregatorsAccessor();

}
