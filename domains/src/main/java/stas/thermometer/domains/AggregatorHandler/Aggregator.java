package stas.thermometer.domains.AggregatorHandler;


import stas.thermometer.domains.Measurement;

public class Aggregator {

    private final AggregatorAlarm alarm;
    private final AggregatorCorrective Corrective;




    public Aggregator() {
        this.alarm = new AggregatorAlarm();
        this.Corrective = new AggregatorCorrective();
    }


    public void adjustDelta(double correctiveDelta) {
        this.Corrective.adjustCorrectiveDelta(correctiveDelta);
    }

    public double execute(double value) {
        double modTemp = Corrective.execute(value);
        alarm.execute(value, modTemp);
        return modTemp;
    };

}
