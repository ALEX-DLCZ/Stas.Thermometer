package stas.thermometer.infrastructures.database;

import stas.thermometer.infrastructures.database.dbexceptions.DBConnectException;
import stas.thermometer.infrastructures.database.dbexceptions.DBInsertException;

public interface AlertRepository extends DataMapper<Alert> {

    //Alert getBy(String modele);
    //void saveVoitureMoteurs(Voiture voiture, List<Moteur> moteurs) throws RepositoryException;
    void saveAlerte(Alert entity) throws DBInsertException, DBConnectException;

}
