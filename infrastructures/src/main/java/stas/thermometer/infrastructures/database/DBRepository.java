package stas.thermometer.infrastructures.database;

import stas.thermometer.domains.ThermometerInterface;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import java.util.List;

public class DBRepository {

    private final List<AggregatorAccessor> aggregatorAccessors;


    public DBRepository(ThermometerInterface thermometerInterface) {
        this.aggregatorAccessors = thermometerInterface.getAggregatorsAccessor();

        for (AggregatorAccessor aggregatorAccessor : aggregatorAccessors) {
            aggregatorAccessor.addSubscriber(this::updateAggregatorNotificatio);
        }


    }
    //todo changer nom
    private void updateAggregatorNotificatio(String aggregatorName) {
        System.out.println(" DB GET NOTIFIED " + aggregatorName);
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