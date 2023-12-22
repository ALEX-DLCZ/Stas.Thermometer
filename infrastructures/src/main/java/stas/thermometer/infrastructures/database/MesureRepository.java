package stas.thermometer.infrastructures.database;

import stas.thermometer.infrastructures.database.dbexceptions.DBConnectException;
import stas.thermometer.infrastructures.database.dbexceptions.DBInsertException;

public interface MesureRepository extends DataMapper<Mesure> {
    int getMesureId(Mesure mesure);

    void saveMesure(Mesure entity) throws DBInsertException, DBConnectException;
}
