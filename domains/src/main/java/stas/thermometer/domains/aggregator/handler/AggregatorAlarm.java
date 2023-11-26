package stas.thermometer.domains.aggregator.handler;

/**
 * @implNote cette classe permet de gérer les alarmes de l'aggrégateur. elle permet de déterminer le type d'alarme.
 *
 * Cohesive set: la class est relativement courte et ne fait qu'une seule chose. impossibilité de la découper en sous-classe.
 *
 */
public class AggregatorAlarm {
    private int alarmType = 0;

    public int getAlarmType() {
        return alarmType;
    }

    public void execute(double temp, double modTemp) {

        if ( modTemp > (temp * 1.1) ) {
            this.alarmType = 1;
        }
        else if ( modTemp < (temp * 0.9) ) {
            this.alarmType = -1;
        }
        else {
            this.alarmType = 0;
        }

    }
}
