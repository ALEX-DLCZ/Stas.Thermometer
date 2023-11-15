package stas.thermometer.domains;

public enum ValueType {

    TEMPERATURE("temperature", ProbeTemperature.class),
    HUMIDITY("humidity", ProbeHumidity.class);

    public final String type;
    private final Class<? extends Probe> probeClass;

    ValueType(String type, Class<? extends Probe> probeClass) {

        this.type = type;
        this.probeClass = probeClass;
    }

    public String getType() {
        return type;
    }

    public Class<? extends Probe> probeClass() {
        return probeClass;
    }
}
