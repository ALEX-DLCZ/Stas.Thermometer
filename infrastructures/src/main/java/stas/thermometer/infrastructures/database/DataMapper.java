package stas.thermometer.infrastructures.database;


import stas.thermometer.infrastructures.database.dbexceptions.RepositoryException;


public interface DataMapper<T> {
    void save(T entity) throws RepositoryException;

//    List<T> getAll();
//    void update(T entity);
//    void delete(T entity);
}