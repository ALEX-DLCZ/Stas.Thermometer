package stas.thermometer.infrastructures.database;

import stas.thermometer.infrastructures.database.dbexceptions.DBConnectException;
import stas.thermometer.infrastructures.database.dbexceptions.DBInsertException;

import java.sql.*;

public class DBMesure extends DBDataMapper<Mesure> implements MesureRepository {
    private final DBRepoObjectMapper<Mesure> referenceMapper;

    public DBMesure(String connectionString) {
        super(connectionString, "Mesures");
        this.referenceMapper = new DBRepoObjectMapper<>();
    }

    @Override
    public int getMesureId(Mesure mesure) {
        return referenceMapper.getObjRef(mesure);
    }


    @Override
    public void saveMesure(Mesure entity) throws DBInsertException, DBConnectException {


        DBConnector connector = new DBConnector();
        try (Connection connection = connector.getConnection(connectionString)) {
            connection.setAutoCommit(false);

            int id = saveAndGetReference(entity);
            referenceMapper.addReference(entity, id);

            connection.commit();
        } catch (SQLException e) {
            throw new DBConnectException();
        }




    }

}

//public class DBMesure extends DBDataMapper<Mesure> implements MesureRepository {
//
//    public DBMesure(String connectionString) {
//        super(connectionString, "Mesures");
//    }
//
//    @Override
//    public int getMesureId(Mesure mesure) {
//        return retrieveObjRef(mesure);
//    }
//
//}
