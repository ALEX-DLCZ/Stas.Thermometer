package stas.thermometer.domains;

import java.time.LocalDateTime;
import java.util.List;

public class ProbeTemperature implements Probe{

    private final List<Double> profil;
    private Measurement currentMeasurement;

    public ProbeTemperature(List<Double> profil) {
        this.profil = profil;
    }

    @Override
    public void generateMeasurement(LocalDateTime dateTime ) {

        currentMeasurement = new Measurement(0, dateTime, MeasurementType.TEMPERATURE);

    }

    @Override
    public Measurement getMeasurement() {
        return currentMeasurement;
    }
}
