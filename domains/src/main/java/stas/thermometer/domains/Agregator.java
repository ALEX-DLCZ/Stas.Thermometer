package stas.thermometer.domains;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Agregator {

    private final Probe probe;
    private LinkedList<Measurement> agregatedValues;
    public Agregator(Probe probe) {
        this.probe = probe;
    }


    public void updateAgregatedValues() {
        LocalDateTime date = LocalDateTime.now();
        probe.generateMeasurement(date);
        agregatedValues.addLast(probe.getMeasurement());

        if (agregatedValues.getLast().dateTime().getSecond() - agregatedValues.getFirst().dateTime().getSecond() > 1) {
            // TODO: 10/11/2023 updateAverageTemperature(); execute une méthode de la classe Presenteur qu'on récupure avant
            System.out.println("updateAverageTemperature");
            agregatedValues.clear();
        }
    }
}
