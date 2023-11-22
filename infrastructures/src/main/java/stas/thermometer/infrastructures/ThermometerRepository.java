package stas.thermometer.infrastructures;

import stas.thermometer.domains.*;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;
import stas.thermometer.infrastructures.database.DBRepository;

import java.util.List;
import java.util.Map;

public class ThermometerRepository implements ThermometerRepositoryInterface {

    private final Thermometer thermometer;
    private final Map<String, String> format;
    private final String name;
    private final DBRepository dbRepository;

    public ThermometerRepository(ConfigurationReader configurationReader) {
        Configuration configuration = new Configuration(configurationReader);

        this.thermometer = new Thermometer();
        for (AggregatorMain aggregator : configuration.getAggregator()) {
            thermometer.Subscribe(aggregator);
        }

        this.name = configuration.getThermometerName();
        this.format = configuration.getFormat();

        this.dbRepository = new DBRepository(this);
    }



    @Override
    public String getThermometerName() {
        return this.name;
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
