package stas.thermometer.infrastructures.database;

import java.util.Map;
import java.util.WeakHashMap;

public class DBRepoObjectMapper<T> {
    private final Map<T, Integer> objRefMap = new WeakHashMap<>();

    public void addReference(T entity, int id) {
        objRefMap.put(entity, id);
    }

    public int getObjRef(T entity) {
        return objRefMap.getOrDefault(entity, -1);
    }
}