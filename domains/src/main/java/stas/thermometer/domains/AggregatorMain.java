package stas.thermometer.domains;


import stas.thermometer.domains.AggregatorHandler.Aggregator;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class AggregatorMain {

    private final Probe probe;
    private final Aggregator aggregator;
    private LinkedList<Measurement> aggregatedValues;


    public AggregatorMain(Probe probe) {
        this.probe = probe;
        this.aggregator = new Aggregator();
    }


    public void updateAgregatedValues() {
        LocalDateTime date = LocalDateTime.now();
        probe.generateMeasurement(date);
        aggregatedValues.add(probe.getMeasurement());

        if (!aggregatedValues.isEmpty() && aggregatedValues.getLast().dateTime().getSecond() - aggregatedValues.getFirst().dateTime().getSecond() > 2) {
            // TODO: 14/11/2023 updateAverageTemperature(); execute une méthode de la classe Presenteur qu'on récupure avant

            double averageList = aggregatedValues.stream().mapToDouble(Measurement::value).average().orElse(0.0);

            Measurement measurement = new Measurement(aggregator.execute(averageList), date);
            aggregatedValues.clear();
        }
    }


    public void adjustDelta(double correctiveDelta) {
        this.aggregator.adjustDelta(correctiveDelta);
    }
}
