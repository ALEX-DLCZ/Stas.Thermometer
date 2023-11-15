package stas.thermometer.domains;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private final Thermometer thermometer;
    private final Map<String, String> format;

    public Configuration(ConfigurationReader reader) {
        Map<String, Map<String, String>> readedConfiguration = reader.getReadedConfiguration();
        this.format = readedConfiguration.get("format");

        this.thermometer = new Thermometer();

    }
    public Thermometer getThermometer() {
        return this.thermometer;
    }

    public Map<String, String> getFormat() {
        return format;
    }


    public ValueType getValueType(String type) {
        return ValueType.valueOf(type.toUpperCase());
    }
}
