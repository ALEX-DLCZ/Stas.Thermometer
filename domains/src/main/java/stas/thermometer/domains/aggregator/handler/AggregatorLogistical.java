package stas.thermometer.domains.aggregator.handler;


import stas.thermometer.domains.Measurement;

import java.time.LocalDateTime;

public class AggregatorLogistical {

    private final AggregatorAlarm alarm;
    private final AggregatorCorrective corrective;
    private final AggregatorValueUpdater aggregatorValueUpdater;
    private Measurement measurementMod;


    public AggregatorLogistical(AggregatorValueUpdater aggregatorValueUpdater) {
        this.alarm = new AggregatorAlarm();
        this.corrective = new AggregatorCorrective();
        this.aggregatorValueUpdater = aggregatorValueUpdater;
    }


    public void adjustDelta(double correctiveDelta) {
        this.corrective.adjustCorrectiveDelta(correctiveDelta);
    }


    public boolean update(){
        if( this.aggregatorValueUpdater.updater()){
            this.measurementMod = new Measurement (execute(this.aggregatorValueUpdater.getAverageMeasurement()), LocalDateTime.now());
            return true;
        }
        return false;
    }

    private double execute(double value) {
        double modTemp = corrective.execute(value);
        alarm.execute(value, modTemp);
        return modTemp;
    };

    public int getAlarmType() {
        return alarm.getAlarmType();
    }

    public Measurement getMeasurementMod() {
        return this.measurementMod;
    }

    public Measurement getMeasurementSimple() {
        return this.aggregatorValueUpdater.getMeasurementSimple();
    }
}
