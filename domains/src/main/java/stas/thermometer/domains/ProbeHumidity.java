package stas.thermometer.domains;

import java.time.LocalDateTime;
import java.util.List;

public class ProbeHumidity implements Probe{


    private final List<Double> profil;
    private Measurement currentMeasurement;

    public ProbeHumidity(List<Double> profil) {
        this.profil = profil;
    }


    @Override
    public void generateMeasurement(LocalDateTime dateTime) {
        currentMeasurement = new Measurement(currentValue(dateTime), dateTime, MeasurementType.HUMIDITY);


    }

    @Override
    public Measurement getMeasurement() {
        return currentMeasurement;
    }



    private double currentValue(LocalDateTime dateTime) {
        int nbList = profil.size();
        int time = dateTime.toLocalTime().toSecondOfDay();

        //détermine l'index de la liste dans laquelle nous nous trouvons
        int timeSecond = 86400;
        int index = (int) Math.floor((double) (time * nbList) / timeSecond);
        //récupère les valeur des éléments de la liste entre lesquels nous nous trouvons
        double value1 = profil.get(index);

        double value2;
        try {
            value2 = profil.get(index + 1);
        } catch (IndexOutOfBoundsException e) {
            value2 = profil.get(0);
        }

        return value1 + ((value2 - value1) * ((time * nbList) % timeSecond) / timeSecond);
    }
}
