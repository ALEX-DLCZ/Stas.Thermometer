package stas.thermometer.domains;

import java.util.List;

public class Thermometer {

    private final String name;

    private List<AggregatorMain> aggregatorMains;

    public Thermometer(String name) {
        this.name = name;
    }


    public void Subscribe(AggregatorMain aggregatorMain) {
        aggregatorMains.add(aggregatorMain);
    }

    public void Unsubscribe(AggregatorMain aggregatorMain) {
        aggregatorMains.remove(aggregatorMain);
    }
    public void NotifySubscribers() {
        for (AggregatorMain aggregatorMain : aggregatorMains) {
            aggregatorMain.updateAgregatedValues();
        }
    }

}
