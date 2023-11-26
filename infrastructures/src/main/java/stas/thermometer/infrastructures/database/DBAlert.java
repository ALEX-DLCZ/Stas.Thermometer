package stas.thermometer.infrastructures.database;

public class DBAlert extends DBDataMapper<Alert> implements AlertRepository {

    public DBAlert(String connectionString) {

        super(connectionString, "Alerts", Alert.class);
    }
}
