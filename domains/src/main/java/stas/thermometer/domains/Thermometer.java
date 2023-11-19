package stas.thermometer.domains;

import java.util.ArrayList;
import java.util.List;

public class Thermometer {

    private final String name;
    private List<AggregatorMain> aggregatorMains;

    public Thermometer(String name) {
        this.name = name;
        this.aggregatorMains = new ArrayList<>();
    }

    public List<AggregatorMain> getAggregators() {
        return aggregatorMains;
    }


    //---------------------------------------------
    public void Subscribe(AggregatorMain aggregatorMain) {
        this.aggregatorMains.add(aggregatorMain);
    }
    public void Unsubscribe(AggregatorMain aggregatorMain) {
        this.aggregatorMains.remove(aggregatorMain);
    }
    public void NotifySubscribers() {
        for (AggregatorMain aggregatorMain : this.aggregatorMains) {
            aggregatorMain.updateAgregatedValues();
        }
    }
    //---------------------------------------------
}
