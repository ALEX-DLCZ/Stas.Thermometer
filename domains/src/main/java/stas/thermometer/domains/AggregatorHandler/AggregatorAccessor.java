package stas.thermometer.domains.AggregatorHandler;

import stas.thermometer.domains.AggregatorSubscriber;
import stas.thermometer.domains.Measurement;

public interface AggregatorAccessor {



    String getName();
    Measurement getmesurementMod();
    Measurement getmesurementSimple();
    void adjustDelta(double correctiveDelta);
    int getAlarmType();
    void addSubscriber(AggregatorSubscriber subscriber);
    void removeSubscriber(AggregatorSubscriber subscriber);
}
