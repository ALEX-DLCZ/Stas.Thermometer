package stas.thermometer.infrastructures.database;

import stas.thermometer.domains.ThermometerInterface;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;
import stas.thermometer.infrastructures.database.dbexceptions.RepositoryException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MainDataBase {

    private List<AggregatorAccessor> aggregatorAccessors;
    private final String thermometerName;
    private final MesureRepository mesureRepository;
    private final AlertRepository alertRepository;


    public MainDataBase(String connectionString, ThermometerInterface thermometerInterface) {
        this.aggregatorAccessors = thermometerInterface.getAggregatorsAccessor();
        this.thermometerName = thermometerInterface.getThermometerName();

        for (AggregatorAccessor aggregatorAccessor : aggregatorAccessors) {
            aggregatorAccessor.addSubscriber(this::updateAggregatorNotification);
        }

        this.mesureRepository = new DBMesure(connectionString);
        this.alertRepository = new DBAlert(connectionString);



    }

    //todo changer nom
    private void updateAggregatorNotification(String aggregatorName)  {
        AggregatorAccessor aggregatorAccessor = this.aggregatorAccessors.stream().filter(aggregatorAccessor1 -> aggregatorAccessor1.getName().equals(aggregatorName)).findFirst().get();

        Mesure mesure = new Mesure(this.thermometerName, aggregatorAccessor.getmesurementMod().dateTime(), aggregatorName, "todo", aggregatorAccessor.getmesurementMod().value());
        Alert alert = new Alert(aggregatorAccessor.getmesurementMod().value());


        try {
            mesureRepository.save(mesure);

        if (aggregatorAccessor.getAlarmType() != 0) {
            alert.setIdMesure(mesureRepository.getMesureId());
            alertRepository.save(alert);
        }

        } catch (RepositoryException e) {
            throw new RuntimeException(e);
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