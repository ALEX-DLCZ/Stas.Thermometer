package stas.thermometer.infrastructures.database;

import stas.thermometer.infrastructures.database.dbexceptions.DBInsertException;
import stas.thermometer.infrastructures.database.dbexceptions.RepositoryException;

public interface AlertRepository extends DataMapper<Alert>{

    //Alert getBy(String modele);
    //void saveVoitureMoteurs(Voiture voiture, List<Moteur> moteurs) throws RepositoryException;

}
