package stas.thermometer.infrastructures;

import stas.thermometer.domains.*;

import java.util.Map;

public class ThermometerRepository implements ThermometerRepositoryInterface {

    private final Thermometer thermometer;
    private final Map<String, String> format;

    public ThermometerRepository(ConfigurationReader configurationReader){
        Configuration configuration = new Configuration(configurationReader);

        this.thermometer = configuration.createThermometer();
        for(Probe probe : configuration.getProbes()){
            thermometer.Subscribe( new AggregatorMain(probe));
        }


        this.format = configuration.getFormat();

    }



    @Override
    public Map<String, String> getFormat() {
        return this.format;
    }

    @Override
    public void notifyUpdate() {
        this.thermometer.NotifySubscribers();
    }
}
