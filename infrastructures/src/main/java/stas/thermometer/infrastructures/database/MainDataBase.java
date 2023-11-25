package stas.thermometer.infrastructures.database;

import stas.thermometer.domains.ThermometerInterface;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;
import stas.thermometer.infrastructures.database.dbexceptions.RepositoryException;
import java.util.List;

public class MainDataBase {

    private final List<AggregatorAccessor> aggregatorAccessors;
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

    private void updateAggregatorNotification(String aggregatorName)  {
        AggregatorAccessor aggregatorAccessor = this.aggregatorAccessors.stream().filter(aggregatorAccessor1 -> aggregatorAccessor1.getName().equals(aggregatorName)).findFirst().get();

        //todo changer nom du format
        Mesure mesure = new Mesure(this.thermometerName, aggregatorAccessor.getmesurementMod().dateTime(), aggregatorName, "todo", aggregatorAccessor.getmesurementMod().value());

        //todo changer nom de l'id

        try {

            mesureRepository.save(mesure);
            if (aggregatorAccessor.getAlarmType() != 0) {
            alertRepository.save(new Alert(0.0, mesureRepository.getMesureId(mesure)));
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