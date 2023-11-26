package stas.thermometer.domains;

import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;
import stas.thermometer.domains.aggregator.handler.AggregatorLogistical;
import stas.thermometer.domains.aggregator.handler.AggregatorValueUpdater;

import java.util.ArrayList;
import java.util.List;

public class AggregatorMain implements AggregatorAccessor {

    private final String name;
    private final AggregatorLogistical aggregatorLogistical;
    private final List<AggregatorSubscriber> subscribers = new ArrayList<>();

    public AggregatorMain(String name, Probe probe, double delta) {
        this.name = name;
        this.aggregatorLogistical = new AggregatorLogistical(new AggregatorValueUpdater(probe), delta);
    }


    // notifie les subscribers si les valeurs agregées ont changées
    public void updateAgregatedValues() {
        if ( aggregatorLogistical.update() ) {
            notifySubscribers();
        }
    }

    private void notifySubscribers() {
        for ( AggregatorSubscriber subscriber : subscribers ) {
            subscriber.updateAggregatorNotification(this.name);
        }
    }

    //---------------AggregatorAccessor----------------
    @Override
    public void adjustDelta(boolean isRise) {
        this.aggregatorLogistical.adjustDelta(isRise);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Measurement getmesurementMod() {
        return aggregatorLogistical.getMeasurementMod();
    }

    @Override
    public Measurement getmesurementSimple() {
        return aggregatorLogistical.getMeasurementSimple();
    }

    @Override
    public int getAlarmType() {
        return aggregatorLogistical.getAlarmType();
    }

    //----------
    //permet de s'abonner à un AggregatorMain grace a l'accessor
    @Override
    public void addSubscriber(AggregatorSubscriber subscriber) {subscribers.add(subscriber);}

    @Override
    public void removeSubscriber(AggregatorSubscriber subscriber) {subscribers.remove(subscriber);}

    //----------
}
