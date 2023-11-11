package stas.thermometer.domains;

import java.util.List;

public class Thermometer {

    private final Probe probeTemperature;
    private final Probe probeHumidity;
    private final String name;

    public Thermometer() {
        this.probeTemperature = new ProbeTemperature(List.of(1.0, 2.0, 3.0));
        this.probeHumidity = new ProbeHumidity(List.of(1.0, 2.0, 3.0));
        this.name = "Thermometer";
    }
}
