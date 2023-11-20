package stas.thermometer.domains;


import stas.thermometer.domains.AggregatorHandler.AggregatorAccessor;
import stas.thermometer.domains.AggregatorHandler.AggregatorLogistical;
import stas.thermometer.domains.AggregatorHandler.AggregatorValueUpdater;

import java.util.ArrayList;
import java.util.List;

public class AggregatorMain implements AggregatorAccessor {

    private final String name;
    private final AggregatorLogistical modeler;

    private final List<AggregatorSubscriber> subscribers = new ArrayList<>();



    public AggregatorMain(Probe probe) {
        this.name = probe.getName();

        this.modeler = new AggregatorLogistical(new AggregatorValueUpdater(probe));
    }
    public void updateAgregatedValues() {
        if(modeler.update()) notifySubscribers();
    }
    private void notifySubscribers() {
        for (AggregatorSubscriber subscriber : subscribers) {
            subscriber.updateAggregatorNotification(this.name);
        }
    }


    //--------------------------
    @Override
    public void adjustDelta(double correctiveDelta) {
        this.modeler.adjustDelta(correctiveDelta);
    }

    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public Measurement getmesurementMod() {
        return modeler.getMeasurementMod();
    }
    @Override
    public Measurement getmesurementSimple() {
        return modeler.getMeasurementSimple();
    }
    @Override
    public int getAlarmType() {
        return modeler.getAlarmType();
    }

    //----------
    @Override
    public void addSubscriber(AggregatorSubscriber subscriber) {subscribers.add(subscriber);}
    @Override
    public void removeSubscriber(AggregatorSubscriber subscriber) {subscribers.remove(subscriber);}

    //----------
}
