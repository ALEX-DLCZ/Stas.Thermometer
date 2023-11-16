package stas.thermometer.domains.AggregatorHandler;


public class AggregatorAlarm{

    private boolean isAlarm = false;
    private boolean IsTooHighAlarm = false;


    public boolean getAlarm() {
        return this.isAlarm;
    }
    public boolean tooHighAlarm() {
        return this.IsTooHighAlarm;
    }



    public void execute(double temp, double modTemp) {
        if (modTemp > temp * 1.1) {
            this.isAlarm = true;
            this.IsTooHighAlarm = true;
        } else if (modTemp < temp * 0.9) {
            this.isAlarm = true;
            this.IsTooHighAlarm = false;
        } else {
            this.isAlarm = false;
        }
    }
}
