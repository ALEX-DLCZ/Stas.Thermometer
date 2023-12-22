package stas.thermometer.infrastructures.database;

import stas.thermometer.infrastructures.database.dbexceptions.DBConnectException;
import stas.thermometer.infrastructures.database.dbexceptions.DBInsertException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @param <T> T est un type générique qui représente un dto de la base de données style Mesure ou Alert
 * @implNote cette classe est une implémentation de l'interface DataMapper
 * <p>
 * <p>
 * visibility of constructors, ..., using setAccessible() : dans ce cas, nous sommes obligé de rendre les champs publics
 * pour pouvoir les accéder
 * <p>
 * Cohesive set: ... oui l'erreur est correcte, a éventuellement modifier grace a l'implémentation de l'interface ou autre.
 */
public class DBDataMapper<T> implements DataMapper<T> {
    private final String connectionString;
    private final String tableName;

    public DBDataMapper(String connectionString, String tableName) {
        this.connectionString = connectionString;
        this.tableName = tableName;
    }

    @Override
    public void save(T entity) throws DBInsertException, DBConnectException {
        DBConnector connector = new DBConnector();
        try (Connection connection = connector.getConnection(connectionString)) {
            QueryBuilder<T> queryBuilder = new QueryBuilder<>();
            String insertQuery = queryBuilder.buildSaveQuery(entity, tableName);

            int id = connector.executeInsertion(connection, insertQuery);
            DBRepoObjectMapper<T> referenceMapper = new DBRepoObjectMapper<>();
            referenceMapper.addReference(entity, id);
        } catch (SQLException e) {
            throw new DBConnectException();
        }
    }

    protected int saveAndGetReference(T entity) throws DBInsertException, DBConnectException {
        DBConnector connector = new DBConnector();
        try (Connection connection = connector.getConnection(connectionString)) {
            QueryBuilder<T> queryBuilder = new QueryBuilder<>();
            String insertQuery = queryBuilder.buildSaveQuery(entity, tableName);

            return connector.executeInsertion(connection, insertQuery);
        } catch (SQLException e) {
            throw new DBConnectException();
        }
    }


//
//    private String buildSaveQuery(T entity) {
//        StringBuilder columns = new StringBuilder();
//        StringBuilder values = new StringBuilder();
//        Field[] fields = getAllFields(entity.getClass());
//
//        for ( Field field : fields ) {
//            columns.append(field.getName()).append(", ");
//            values.append("?, ");
//        }
//        columns.delete(columns.length() - 2, columns.length());
//        values.delete(values.length() - 2, values.length());
//
//        return "INSERT INTO " + tableName + " (" + columns.toString() + ") VALUES (" + values.toString() + ")";
//    }
//
//    private Field[] getAllFields(Class<?> inputClass) {
//        Class<?> clazz = inputClass;
//        java.util.List<Field> fields = new java.util.ArrayList<>();
//        while ( clazz != null ) {
//            fields.addAll(java.util.Arrays.asList(clazz.getDeclaredFields()));
//            clazz = clazz.getSuperclass();
//        }
//        return fields.toArray(new Field[0]);
//    }
//
//    private void setParameterValues(PreparedStatement preparedStatement, T entity) throws SQLException {
//        Field[] fields = getAllFields(entity.getClass());
//        try {
//            int parameterIndex = 1;
//            for (Field field : fields) {
//                field.setAccessible(true);
//                Object columnValue = field.get(entity);
//                preparedStatement.setObject(parameterIndex++, columnValue);
//                field.setAccessible(false);
//            }
//        } catch (IllegalAccessException e) {
//            throw new SQLException("Impossible d'accéder au membre", e);
//        }
//    }
//
//    private int executeInsertion(PreparedStatement preparedStatement) throws SQLException {
//        preparedStatement.execute();
//        try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys() ) {
//            if ( generatedKeys.next() ) {
//                return generatedKeys.getInt(1);
//            }
//            else {
//                throw new SQLException("Aucune clé générée après l'insertion.");
//            }
//        }
//    }
//
//    private int saveStatement(Connection connection, T entity,  String insertQuery) throws DBInsertException {
//        try ( PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS) ) {
//            setParameterValues(preparedStatement, entity);
//            return executeInsertion(preparedStatement);
//        } catch ( SQLException e ) {
//            throw new DBInsertException(e);
//        }
//    }
//
//    protected int getObjRef(T entity) {
//        return objRefMap.get(entity);
//    }


    //    private int saveStatement(Connection connection, T entity, Field[] fields, String insertQuery) throws RepositoryException {
    //        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
    //            int parameterIndex = 1;
    //
    //            // Définir les valeurs des paramètres dans la clause VALUES
    //            for (Field field : fields) {
    ////                field.setAccessible(true);
    //                Object columnValue = field.get(entity);
    //                preparedStatement.setObject(parameterIndex++, columnValue);
    //            }
    //
    //            // Exécuter l'insertion
    //            preparedStatement.execute();
    //
    //            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
    //                if (generatedKeys.next()) {
    //                    return generatedKeys.getInt(1);
    //                } else {
    //                    throw new SQLException("Aucune clé générée après l'insertion.");
    //                }
    //            }
    //        }
    //        catch (SQLException e) {
    //            throw new RepositoryException("Erreur SQL", e);
    //        }
    //        catch (IllegalAccessException e) {
    //            throw new RepositoryException("Impossible d'accéder au membre", e);
    //        }
    //    }


    //    @Override
    //    public void update(T entity) {
    //        throw new UnsupportedOperationException("Unimplemented method 'update'");
    //    }
    //
    //    @Override
    //    public void delete(T entity) {
    //        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    //    }


}
