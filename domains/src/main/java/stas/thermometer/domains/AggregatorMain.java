package stas.thermometer.domains;


import stas.thermometer.domains.AggregatorHandler.AggregatorModeler;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class AggregatorMain {

    private final Probe probe;
    private final AggregatorModeler modeler;
    private final LinkedList<Measurement> aggregatedValues;



    public AggregatorMain(Probe probe) {
        this.probe = probe;
        this.modeler = new AggregatorModeler();
        this.aggregatedValues = new LinkedList<>();
    }


    public void updateAgregatedValues() {
        LocalDateTime date = LocalDateTime.now();
        probe.generateMeasurement(date);
        aggregatedValues.add(probe.getMeasurement());


        if (aggregatedValues.getLast().dateTime().getSecond() - aggregatedValues.getFirst().dateTime().getSecond() > 1) {

            // TODO: 14/11/2023 updateAverageTemperature(); execute une méthode de la classe Presenteur qu'on récupure avant
            double averageList = aggregatedValues.stream().mapToDouble(Measurement::value).average().orElse(0.0);

            Measurement measurement = new Measurement(modeler.execute(averageList), date);
            System.out.println("BAAAHH " + measurement.value());
            aggregatedValues.clear();
        }
    }


    public void adjustDelta(double correctiveDelta) {
        this.modeler.adjustDelta(correctiveDelta);
    }
}
