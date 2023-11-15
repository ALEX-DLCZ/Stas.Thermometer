package stas.thermometer.domains;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Aggregator {

    private final Probe probe;

    // TODO: 14/11/2023  pas de concret style Linked ou Hash map
    private LinkedList<Measurement> aggregatedValues;
    public Aggregator(Probe probe) {
        this.probe = probe;
    }


    public void updateAgregatedValues() {
        LocalDateTime date = LocalDateTime.now();
        probe.generateMeasurement(date);
        aggregatedValues.add(probe.getMeasurement());

        if (!aggregatedValues.isEmpty() && aggregatedValues.getLast().dateTime().getSecond() - aggregatedValues.getFirst().dateTime().getSecond() > 1) {
            // TODO: 14/11/2023 updateAverageTemperature(); execute une méthode de la classe Presenteur qu'on récupure avant
            System.out.println("updateAverageTemperature");
            aggregatedValues.clear();
        }
    }
}
