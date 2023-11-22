package stas.thermometer.domains;

public enum ValueType {

    TEMPERATURE("temperature", ProbeTemperature.class, 0.5),
    HUMIDITY("humidity", ProbeHumidity.class, 0.04);

    private final String type;
    //Field probeClass has the same name as a method
    // private final Class<? extends Probe> probeClass;
    private final Class<? extends Probe> probeClass;

    private final double delta;

    ValueType(String type, Class<? extends Probe> probeClass, double delta) {

        this.type = type;
        this.probeClass = probeClass;
        this.delta = delta;
    }

    public String getType() {
        return type;
    }

    public Class<? extends Probe> getProbeClass() {
        return probeClass;
    }

    public double getDelta() {
        return delta;
    }

}
