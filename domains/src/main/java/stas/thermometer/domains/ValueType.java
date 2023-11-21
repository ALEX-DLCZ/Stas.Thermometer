package stas.thermometer.domains;

public enum ValueType {

    TEMPERATURE("temperature", ProbeTemperature.class),
    HUMIDITY("humidity", ProbeHumidity.class);

    private final String type;
    //Field probeClass has the same name as a method
    // private final Class<? extends Probe> probeClass;
    private final Class<? extends Probe> probeClass;

    ValueType(String type, Class<? extends Probe> probeClass) {

        this.type = type;
        this.probeClass = probeClass;
    }

    public String getType() {
        return type;
    }

    public Class<? extends Probe> getProbeClass() {
        return probeClass;
    }
}
