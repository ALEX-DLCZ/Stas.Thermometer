package stas.thermometer.domains.aggregator.handler;

import stas.thermometer.domains.AggregatorSubscriber;
import stas.thermometer.domains.Measurement;

public interface AggregatorAccessor {

    String getName();

    Measurement getmesurementMod();

    Measurement getmesurementSimple();

    void adjustDelta(boolean isRise);

    int getAlarmType();

    void addSubscriber(AggregatorSubscriber subscriber);

    void removeSubscriber(AggregatorSubscriber subscriber);
}
