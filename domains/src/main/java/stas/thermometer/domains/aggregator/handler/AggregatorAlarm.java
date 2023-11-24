package stas.thermometer.domains.aggregator.handler;


public class AggregatorAlarm {
    private int alarmType = 0;

    public int getAlarmType() {
        return alarmType;
    }


    public void execute(double temp, double modTemp) {

        if (modTemp > (temp * 1.1)) {
            this.alarmType = 1;
        } else if (modTemp < (temp * 0.9)) {
            this.alarmType = -1;
        } else {
            this.alarmType = 0;
        }

    }
}
