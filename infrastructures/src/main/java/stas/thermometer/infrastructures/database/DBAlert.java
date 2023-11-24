package stas.thermometer.infrastructures.database;

import stas.thermometer.infrastructures.database.dbexceptions.RepositoryException;

public class DBAlert extends DBDataMapper<Alert> implements AlertRepository{

        private final String tableName = "Alerts";
        public DBAlert(String connectionString) {
            super(connectionString,"Alerts" ,Alert.class);
        }



}
