package stas.thermometer.domains;

import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import java.util.ArrayList;
import java.util.List;

public class Thermometer {

    private final String name;
    private final List<AggregatorMain> aggregatorMains;

    public Thermometer(String name) {
        this.name = name;
        this.aggregatorMains = new ArrayList<>();
    }

    public List<AggregatorAccessor> getAggregators() {
        return new ArrayList<>(this.aggregatorMains);
    }

    public String getName() {
        return name;
    }


    //---------------------------------------------
    public void Subscribe(AggregatorMain aggregatorMain) {
        this.aggregatorMains.add(aggregatorMain);
    }

//    public void Unsubscribe(AggregatorMain aggregatorMain) {
//        this.aggregatorMains.remove(aggregatorMain);
//    }

    public void NotifySubscribers() {
        for (AggregatorMain aggregatorMain : this.aggregatorMains) {
            aggregatorMain.updateAgregatedValues();
        }
    }
    //---------------------------------------------
}
