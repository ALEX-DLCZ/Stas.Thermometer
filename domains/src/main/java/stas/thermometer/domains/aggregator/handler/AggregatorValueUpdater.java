package stas.thermometer.domains.aggregator.handler;

import stas.thermometer.domains.Measurement;
import stas.thermometer.domains.Probe;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

public class AggregatorValueUpdater {

    private static final int SECONDS_BETWEEN = 2;


    private final Probe probe;
    private final List<Measurement> aggregatedValues;
    private Measurement measurementSimple;

    public AggregatorValueUpdater(Probe probe) {
        this.probe = probe;
        this.aggregatedValues = new LinkedList<>();
    }

    public boolean updater() {
        LocalDateTime date = LocalDateTime.now();
        this.probe.generateMeasurement(date);
        this.aggregatedValues.add(probe.getMeasurement());

        long secondsBetween = ChronoUnit.SECONDS.between(aggregatedValues.get(0).dateTime(), aggregatedValues.get(aggregatedValues.size()-1).dateTime());

        if ((SECONDS_BETWEEN -1) >= secondsBetween) {
            return false;
        }
        this.measurementSimple = new Measurement(aggregatedValues.stream().mapToDouble(Measurement::value).average().orElse(0.0), LocalDateTime.now());
        this.aggregatedValues.clear();
        return true;
    }

    public double getAverageMeasurement() {
        return this.measurementSimple.value();
    }

    public Measurement getMeasurementSimple() {
        return this.measurementSimple;

    }
}