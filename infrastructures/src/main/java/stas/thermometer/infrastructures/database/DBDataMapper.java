package stas.thermometer.infrastructures.database;

import stas.thermometer.infrastructures.database.dbexceptions.RepositoryException;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Map;
import java.util.WeakHashMap;

public class DBDataMapper<T> implements DataMapper<T>{

    private final String connectionString;
    private final String tableName;
    private final Class<T> entityClass;
    private Map<T, Integer> objRefMap = new WeakHashMap<T, Integer>();

    public DBDataMapper(String connectionString, String tableName, Class<T> entityClass) {
        this.connectionString = connectionString;
        this.tableName = tableName;
        this.entityClass = entityClass;
    }


    @Override
    public void save(T entity) throws RepositoryException {
        Field[] fields = getAllFields(entityClass);
        //print le fields
        System.out.println("Fields: ");
        for (Field field : fields) {
            System.out.print(field.getName());
            System.out.println(field.getType());
        }

        String insertQuery = buildSaveQuery(fields);
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            int id = saveStatement(connection, entity, fields, insertQuery);
            objRefMap.put(entity, id);
        }
        catch (SQLException e) {
            throw new RepositoryException("SQL exception", e);
        }

    }
    private String buildSaveQuery(Field[] fields) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : fields) {
            field.setAccessible(true);
            String columnName = field.getName();
            columns.append(columnName).append(", ");
            values.append("?, ");
        }
        // Supprimer la virgule en trop à la fin des clauses
        columns.delete(columns.length() - 2, columns.length());
        values.delete(values.length() - 2, values.length());

        return "INSERT INTO " + tableName + " (" + columns.toString() + ") VALUES (" + values.toString() + ")";
    }
    private Field[] getAllFields(Class<?> clazz) {
        // Collecter tous les champs de la classe courante et de ses superclasses
        java.util.List<Field> fields = new java.util.ArrayList<>();
        while (clazz != null) {
            fields.addAll(java.util.Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }
    private int saveStatement(Connection connection, T entity, Field[] fields, String insertQuery) throws RepositoryException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            int parameterIndex = 1;

            // Définir les valeurs des paramètres dans la clause VALUES
            for (Field field : fields) {
                field.setAccessible(true);
                Object columnValue = field.get(entity);
                preparedStatement.setObject(parameterIndex++, columnValue);
            }

            // Exécuter l'insertion
            preparedStatement.execute();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Aucune clé générée après l'insertion.");
                }
            }
        }
        catch (SQLException e) {
            throw new RepositoryException("Erreur SQL", e);
        }
        catch (IllegalAccessException e) {
            throw new RepositoryException("Impossible d'accéder au membre", e);
        }
    }





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
