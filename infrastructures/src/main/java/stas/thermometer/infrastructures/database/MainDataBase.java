package stas.thermometer.infrastructures.database;

import stas.thermometer.domains.ThermometerInterface;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;
import stas.thermometer.infrastructures.database.dbexceptions.DBConnectException;
import stas.thermometer.infrastructures.database.dbexceptions.DBInsertException;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MainDataBase {

    private final List<AggregatorAccessor> aggregatorAccessors;
    private final String thermometerName;
    private final MesureRepository mesureRepository;
    private final AlertRepository alertRepository;
    private final Map<String, String> formatMap;

    private static final Logger LOG = LogManager.getLogger("stas thermometre");

    public MainDataBase(String connectionString, ThermometerInterface thermometerInterface, Map<String, String> formatMap) {
        this.aggregatorAccessors = thermometerInterface.getAggregatorsAccessor();
        this.thermometerName = thermometerInterface.getThermometerName();
        this.formatMap = formatMap;

        for ( AggregatorAccessor aggregatorAccessor : aggregatorAccessors ) {
            aggregatorAccessor.addSubscriber(this::updateAggregatorNotification);
        }

        this.mesureRepository = new DBMesure(connectionString);
        this.alertRepository = new DBAlert(connectionString);


    }

    private void updateAggregatorNotification(String aggregatorName) {
        AggregatorAccessor aggregatorAccessor = this.aggregatorAccessors.stream().filter(aggregatorAccessor1 -> aggregatorAccessor1.getName().equals(aggregatorName)).findFirst().get();

        Mesure mesure = new Mesure(this.thermometerName, aggregatorAccessor.getmesurementMod().dateTime(), aggregatorName, formatMap.get(aggregatorName), aggregatorAccessor.getmesurementMod().value());

        try {
            mesureRepository.save(mesure);
            if ( aggregatorAccessor.getAlarmType() != 0 ) {
                alertRepository.save(new Alert(aggregatorAccessor.getmesurementSimple().value(), mesureRepository.getMesureId(mesure)));
            }
        } catch ( DBInsertException | DBConnectException e ) {
            LOG.error(e.getMessage());
        }
    }

}



/*
-- Table 'Mesure'
CREATE TABLE Mesure (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         thermometer_name VARCHAR(255) NOT NULL,
                         datetime DATETIME NOT NULL,
                         type VARCHAR(50) NOT NULL,
                         format VARCHAR(50) NOT NULL,
                         value DOUBLE NOT NULL
);

-- Table 'Alert'
CREATE TABLE Alert (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        expected_value DOUBLE NOT NULL,
                        id_mesure INT NOT NULL,
                        FOREIGN KEY (id_mesure) REFERENCES Mesure(id)
);
 */