package stas.thermometer.infrastructures;

import stas.thermometer.domains.Configuration;
import stas.thermometer.domains.ConfigurationReader;
import stas.thermometer.domains.Thermometer;
import stas.thermometer.domains.ThermometerRepositoryInterface;

import java.util.HashMap;
import java.util.Map;

public class ThermometerRepository implements ThermometerRepositoryInterface {

    private final Thermometer thermometer;
    private final Map<String, String> format;

    public ThermometerRepository(ConfigurationReader configurationReader){
        Configuration configuration = new Configuration(configurationReader);
        this.thermometer = configuration.getThermometer();
        this.format = configuration.getFormat();

    }

    @Override
    public Map<String, String> getFormat() {
        return this.format;
    }
}
