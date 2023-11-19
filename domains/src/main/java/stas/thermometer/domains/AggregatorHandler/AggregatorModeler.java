package stas.thermometer.domains.AggregatorHandler;


public class AggregatorModeler {

    private final AggregatorAlarm alarm;
    private final AggregatorCorrective Corrective;




    public AggregatorModeler() {
        this.alarm = new AggregatorAlarm();
        this.Corrective = new AggregatorCorrective();
        System.out.println("AggregatorModeler created");
    }


    public void adjustDelta(double correctiveDelta) {
        this.Corrective.adjustCorrectiveDelta(correctiveDelta);
    }

    public double execute(double value) {
        double modTemp = Corrective.execute(value);
        alarm.execute(value, modTemp);
        return modTemp;
    };

    public int getAlarmType() {
        return alarm.getAlarmType();
    }

}
