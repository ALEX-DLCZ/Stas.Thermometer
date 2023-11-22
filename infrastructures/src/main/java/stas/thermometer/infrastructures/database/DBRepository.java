package stas.thermometer.infrastructures.database;

import stas.thermometer.domains.Configuration;
import stas.thermometer.domains.ConfigurationReader;
import stas.thermometer.domains.Thermometer;
import stas.thermometer.domains.ThermometerRepositoryInterface;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;
import stas.thermometer.infrastructures.ThermometerRepository;

import java.util.List;

public class DBRepository {

    private final List<AggregatorAccessor> aggregatorAccessors;


    public DBRepository(ThermometerRepositoryInterface thermometerRepositoryInterface) {
        this.aggregatorAccessors = thermometerRepositoryInterface.getAggregatorsAccessor();

        for (AggregatorAccessor aggregatorAccessor : aggregatorAccessors) {
            aggregatorAccessor.addSubscriber(this::updateAggregatorNotificatio);
        }


    }

    private void updateAggregatorNotificatio(String s) {
        System.out.println(" DB GET NOTIFIED " + s);
    }


}



/*
-- Table 'Mesures'
CREATE TABLE Mesures (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         thermometer_name VARCHAR(255) NOT NULL,
                         datetime DATETIME NOT NULL,
                         type VARCHAR(50) NOT NULL,
                         format VARCHAR(50) NOT NULL,
                         value DOUBLE NOT NULL
);

-- Table 'Alerts'
CREATE TABLE Alerts (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        expected_value DOUBLE NOT NULL,
                        id_mesure INT NOT NULL,
                        FOREIGN KEY (id_mesure) REFERENCES Mesures(id)
);
 */