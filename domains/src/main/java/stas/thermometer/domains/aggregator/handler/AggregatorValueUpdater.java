package stas.thermometer.domains.aggregator.handler;

import stas.thermometer.domains.Measurement;
import stas.thermometer.domains.Probe;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

/**
 * @implNote cette classe permet de mettre à jour les valeurs des sondes pour en faire une moyenne. Elle est l'une des classes principales de l'aggrégateur.
 * <p>
 * Cohesive set: malgré les 4 fields, la méthode updater() les utilise toutes et ne fait qu'une seule chose. impossibilité de la découper en sous-classe.
 * SECONDS_BETWEEN, probe, aggregatedValues et measurementSimple sont tous liés à la méthode updater()
 * et ne sont utilisé uniquement dans cette classe a l'exceptions de MesurementSimple qui est utilisé dans AggregatorMain.
 */
public class AggregatorValueUpdater {

    private static final int SECONDS_BETWEEN = 2;
    private final Probe probe;
    private final List<Measurement> aggregatedValues;
    private Measurement measurementSimple;

    public AggregatorValueUpdater(Probe probe) {
        this.probe = probe;
        this.aggregatedValues = new LinkedList<>();
        this.measurementSimple = new Measurement(0.0, LocalDateTime.now());
    }

    public boolean updater() {
        LocalDateTime date = LocalDateTime.now();
        this.aggregatedValues.add(probe.generateMeasurement(date));

        long secondsBetween = ChronoUnit.SECONDS.between(aggregatedValues.get(0).dateTime(), aggregatedValues.get(aggregatedValues.size() - 1).dateTime());
        if ( (SECONDS_BETWEEN - 1) >= secondsBetween ) {
            return false;
        }
        this.measurementSimple = new Measurement(aggregatedValues.stream().mapToDouble(Measurement::value).average().orElse(0.0), LocalDateTime.now());
        this.aggregatedValues.clear();
        return true;
    }

    public Measurement getMeasurementSimple() {
        return this.measurementSimple;
    }

}
