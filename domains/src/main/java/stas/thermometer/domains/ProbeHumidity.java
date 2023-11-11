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
        // TODO: Generate Real Measurement
        currentMeasurement = new Measurement(0, dateTime, MeasurementType.HUMIDITY);


    }

    @Override
    public Measurement getMeasurement() {
        return currentMeasurement;
    }
}
