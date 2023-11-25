package stas.thermometer.infrastructures.database;

import stas.thermometer.infrastructures.database.dbexceptions.DBConnectException;
import stas.thermometer.infrastructures.database.dbexceptions.DBInsertException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.WeakHashMap;


/**
 * @implNote cette classe est une implémentation de l'interface DataMapper
 *
 *
 * visibility of constructors, ..., using setAccessible() : dans ce cas, nous sommes obligé de rendre les champs publics
 * pour pouvoir les accéder
 *
 *
 *
 * @param <T> T est un type générique qui représente un dto de la base de données style Mesure ou Alert
 */
public class DBDataMapper<T> implements DataMapper<T>{

    private final String connectionString;
    private final String tableName;
    private final Class<T> entityClass;
    private final Map<T, Integer> objRefMap = new WeakHashMap<T, Integer>();

    public DBDataMapper(String connectionString, String tableName, Class<T> entityClass) {
        this.connectionString = connectionString;
        this.tableName = tableName;
        this.entityClass = entityClass;
    }


    @Override
    public void save(T entity) throws DBInsertException, DBConnectException {
        Field[] fields = getAllFields(entityClass);

        String insertQuery = buildSaveQuery(fields);
        try (Connection connection = DriverManager.getConnection(connectionString)) {

            int id = saveStatement(connection, entity, fields, insertQuery);
            objRefMap.put(entity, id);
        }
        catch (SQLException e) {
            throw new DBConnectException();
        }

    }
    private String buildSaveQuery(Field[] fields) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : fields) {
//            field.setAccessible(true);
            String columnName = field.getName();
            columns.append(columnName).append(", ");
            values.append("?, ");
        }
        columns.delete(columns.length() - 2, columns.length());
        values.delete(values.length() - 2, values.length());

        return "INSERT INTO " + tableName + " (" + columns.toString() + ") VALUES (" + values.toString() + ")";
    }
    private Field[] getAllFields(Class<?> inputClass) {
        Class<?> clazz = inputClass;
        java.util.List<Field> fields = new java.util.ArrayList<>();
        while (clazz != null) {
            fields.addAll(java.util.Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }

    private void setParameterValues(PreparedStatement preparedStatement, T entity, Field[] fields) throws IllegalAccessException, SQLException {
        int parameterIndex = 1;
        for (Field field : fields) {
            field.setAccessible(true);
            Object columnValue = field.get(entity);
            preparedStatement.setObject(parameterIndex++, columnValue);
        }
    }

    private int executeInsertion(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Aucune clé générée après l'insertion.");
            }
        }
    }

    private int saveStatement(Connection connection, T entity, Field[] fields, String insertQuery) throws DBInsertException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            setParameterValues(preparedStatement, entity, fields);
            return executeInsertion(preparedStatement);
        } catch (SQLException e) {
            throw new DBInsertException( e);
        } catch (IllegalAccessException e) {
            throw new DBInsertException("Impossible d'accéder au membre", e);
        }
    }


    protected int getObjRef(T entity) {
        return objRefMap.get(entity);
    }







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
