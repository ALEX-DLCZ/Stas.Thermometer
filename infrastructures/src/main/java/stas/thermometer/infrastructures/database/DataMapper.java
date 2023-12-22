package stas.thermometer.infrastructures.database;

import stas.thermometer.infrastructures.database.dbexceptions.DBConnectException;
import stas.thermometer.infrastructures.database.dbexceptions.DBInsertException;

public interface DataMapper<T> {
    void save(T entity) throws DBInsertException, DBConnectException;

    int saveAndGetReference(T entity) throws DBInsertException, DBConnectException;

    // autre méthode que l'on pourrait avoir dans un DataMapper
    //    List<T> getAll();
    //    void update(T entity);
    //    void delete(T entity);
}