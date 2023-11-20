package stas.thermometer.domains.AggregatorHandler;


import stas.thermometer.domains.Measurement;

import java.time.LocalDateTime;

public class AggregatorLogistical {

    private final AggregatorAlarm alarm;
    private final AggregatorCorrective Corrective;
    private final AggregatorValueUpdater aggregatorValueUpdater;
    private Measurement measurementMod;


    public AggregatorLogistical(AggregatorValueUpdater aggregatorValueUpdater) {
        this.alarm = new AggregatorAlarm();
        this.Corrective = new AggregatorCorrective();
        this.aggregatorValueUpdater = aggregatorValueUpdater;
    }


    public void adjustDelta(double correctiveDelta) {
        this.Corrective.adjustCorrectiveDelta(correctiveDelta);
    }


    public void update(){
        if( this.aggregatorValueUpdater.updater()){
            this.measurementMod = new Measurement (execute(this.aggregatorValueUpdater.getAverageMeasurement()), LocalDateTime.now());
            System.out.println("AggregatorLogistical: " + this.measurementMod.value());
        }
    }

    private double execute(double value) {
        double modTemp = Corrective.execute(value);
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
