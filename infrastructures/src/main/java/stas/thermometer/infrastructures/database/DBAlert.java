package stas.thermometer.infrastructures.database;

import stas.thermometer.infrastructures.database.dbexceptions.DBConnectException;
import stas.thermometer.infrastructures.database.dbexceptions.DBInsertException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBAlert extends DBDataMapper<Alert> implements AlertRepository {

    public DBAlert(String connectionString) {
        super(new DBConnector(connectionString), "Alerts");

    }

    @Override
    public void saveAlerte(Alert entity) throws DBInsertException, DBConnectException {


        try ( Connection connection = dbConnector.getConnection() ) {

            try {
                connection.setAutoCommit(false);
                saveAndGetReference(entity);
                connection.commit();
            } catch ( SQLException e ) {
                connection.rollback();
                throw new DBInsertException();
            } finally {
                dbConnector.disconnect();
            }
        } catch ( SQLException e ) {
            throw new DBConnectException();
        }

    }


    //    @Override
    //    public void saveAlerte(Alert entity) throws DBInsertException, DBConnectException {
    //        Connection connection = null;
    //        try {
    //            connection = dbConnector.getConnection();
    //            connection.setAutoCommit(false);
    //            saveAndGetReference(entity);
    //            connection.commit();
    //        } catch (SQLException e) {
    //            if (connection != null) {
    //                try {
    //                    connection.rollback();
    //                } catch (SQLException rollbackException) {
    //                    // Gérer l'exception de rollback si nécessaire
    //                    throw new DBInsertException(rollbackException);
    //                }
    //            }
    //            throw new DBConnectException();
    //        } finally {
    //            if (connection != null) {
    //                try {
    //                    connection.setAutoCommit(true);
    //                    dbConnector.disconnect();
    //                } catch (SQLException disconnectException) {
    //                    // Gérer l'exception de déconnexion si nécessaire
    //                    disconnectException.printStackTrace();
    //                }
    //            }
    //        }
    //    }

}
