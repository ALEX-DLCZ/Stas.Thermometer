package stas.thermometer.infrastructures.database;

import java.util.StringJoiner;
import java.lang.reflect.Field;

public class QueryBuilder<T> {


    public String buildInsertQuery(T entity, String tableName) {
        Field[] fields = entity.getClass().getDeclaredFields();
        StringJoiner columns = new StringJoiner(", ");
        StringJoiner values = new StringJoiner(", ");
        for (Field field : fields) {
            columns.add(field.getName());
            values.add("?");
        }
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
    }


//    public String buildSaveQuery(Field[] fields, String tableName)   {
//
//
//        StringJoiner columns = new StringJoiner(", ");
//        StringJoiner values = new StringJoiner(", ");
//
//        for (Field field : fields) {
//            columns.add(field.getName());
//            values.add("?");
//        }
//
//        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
//    }

//    public Field[] getAllFields(Class<?> inputClass) {
//        Class<?> clazz = inputClass;
//        java.util.List<Field> fields = new java.util.ArrayList<>();
//        while (clazz != null) {
//            fields.addAll(java.util.Arrays.asList(clazz.getDeclaredFields()));
//            clazz = clazz.getSuperclass();
//        }
//        return fields.toArray(new Field[0]);
//    }


}
