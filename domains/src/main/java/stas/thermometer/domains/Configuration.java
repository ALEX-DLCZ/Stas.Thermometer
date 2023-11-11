package stas.thermometer.domains;

import java.util.HashMap;

public class Configuration {

    private final Thermometer thermometer;
    private final HashMap<String, String> format;

    public Configuration(ConfigurationReader reader) {
        HashMap<String, HashMap<String, String>> readedConfiguration = reader.getReadedConfiguration();
        this.format = readedConfiguration.get("format");

        this.thermometer = new Thermometer();

    }
    public Thermometer getThermometer() {
        return this.thermometer;
    }

    public HashMap<String, String> getFormat() {
        return format;
    }
}
