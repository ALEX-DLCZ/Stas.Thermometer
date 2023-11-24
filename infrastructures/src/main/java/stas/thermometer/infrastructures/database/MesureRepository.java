package stas.thermometer.infrastructures.database;

import stas.thermometer.infrastructures.database.dbexceptions.RepositoryException;

public interface MesureRepository extends DataMapper<Mesure>{
    int getMesureId() throws RepositoryException;
}
