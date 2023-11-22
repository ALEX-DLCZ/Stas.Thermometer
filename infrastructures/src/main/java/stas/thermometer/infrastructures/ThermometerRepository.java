package stas.thermometer.infrastructures;

import stas.thermometer.domains.*;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import java.util.List;
import java.util.Map;

public class ThermometerRepository implements ThermometerRepositoryInterface {

    private final Thermometer thermometer;
    private final Map<String, String> format;

    public ThermometerRepository(ConfigurationReader configurationReader) {
        Configuration configuration = new Configuration(configurationReader);

        this.thermometer = configuration.createThermometer();
        for (AggregatorMain aggregator : configuration.getAggregator()) {
            thermometer.Subscribe(aggregator);
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

    @Override
    public List<AggregatorAccessor> getAggregatorsAccessor() {
        return this.thermometer.getAggregators();
    }
}
