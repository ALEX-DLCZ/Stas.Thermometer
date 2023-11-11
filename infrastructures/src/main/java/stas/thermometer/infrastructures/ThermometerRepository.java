package stas.thermometer.infrastructures;

import stas.thermometer.domains.Configuration;
import stas.thermometer.domains.ConfigurationReader;
import stas.thermometer.domains.Thermometer;
import stas.thermometer.domains.ThermometerRepositoryInterface;

import java.util.HashMap;

public class ThermometerRepository implements ThermometerRepositoryInterface {

    private final Thermometer thermometer;
    private final HashMap<String, String> format;

    public ThermometerRepository(ConfigurationReader configurationReader){
        Configuration configuration = new Configuration(configurationReader);
        this.thermometer = configuration.getThermometer();
        this.format = configuration.getFormat();

    }

    @Override
    public HashMap<String, String> getFormat() {
        return this.format;
    }
}
