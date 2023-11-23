package stas.thermometer.infrastructures;

import stas.thermometer.domains.AggregatorMain;
import stas.thermometer.domains.ThermometerInterface;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import java.util.ArrayList;
import java.util.List;

public class Thermometer implements ThermometerInterface {

    private final String name;
    private final List<AggregatorMain> aggregatorMains;

    public Thermometer(String name) {
        this.name = name;
        this.aggregatorMains = new ArrayList<>();
    }


    @Override
    public String getThermometerName() {
        return this.name;
    }
    @Override
    public List<AggregatorAccessor> getAggregatorsAccessor() {
        return new ArrayList<>(this.aggregatorMains);
    }



    //--------------------------
    public void Subscribe(AggregatorMain aggregatorMain) {
        this.aggregatorMains.add(aggregatorMain);
    }

    //    public void Unsubscribe(AggregatorMain aggregatorMain) {
//        this.aggregatorMains.remove(aggregatorMain);
//    }
    @Override
    public void notifyUpdate() {
        for (AggregatorMain aggregatorMain : this.aggregatorMains) {
            aggregatorMain.updateAgregatedValues();
        }
    }
    //--------------------------

}
