package stas.thermometer.domains.AggregatorHandler;

import stas.thermometer.domains.Measurement;
import stas.thermometer.domains.Probe;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

public class AggregatorValueUpdater {

    private final Probe probe;
    private final LinkedList<Measurement> aggregatedValues;
    private Measurement measurementSimple;

    public AggregatorValueUpdater(Probe probe) {
        this.probe = probe;
        this.aggregatedValues = new LinkedList<>();
    }

    public boolean updater() {
        LocalDateTime date = LocalDateTime.now();
        this.probe.generateMeasurement(date);
        this.aggregatedValues.add(probe.getMeasurement());

        long secondsBetween = ChronoUnit.SECONDS.between(aggregatedValues.getFirst().dateTime(), aggregatedValues.getLast().dateTime());
        if (secondsBetween  > 1) {
            this.measurementSimple = new Measurement( aggregatedValues.stream().mapToDouble(Measurement::value).average().orElse(0.0),LocalDateTime.now());
            this.aggregatedValues.clear();
            return true;
        }
        return false;
    }

    public double getAverageMeasurement() {
        return this.measurementSimple.value();
    }

    public Measurement getMeasurementSimple() {
        return this.measurementSimple;

    }
}
