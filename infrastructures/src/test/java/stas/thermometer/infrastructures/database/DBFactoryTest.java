package stas.thermometer.infrastructures.database;



//public class DBAlert extends DBDataMapper<Alert> implements AlertRepository {
//
//    public DBAlert(String connectionString) {
//        super(new DBConnector(connectionString), "Alerts");
//
//    }
//
//    @Override
//    public void saveAlerte(Alert entity) throws DBInsertException, DBConnectException {
//
//
//        try ( Connection connection = dbConnector.getConnection() ) {
//
//            try {
//                connection.setAutoCommit(false);
//                saveAndGetReference(entity);
//                connection.commit();
//            } catch ( SQLException e ) {
//                connection.rollback();
//                throw new DBInsertException();
//            } finally {
//                dbConnector.disconnect();
//            }
//        } catch ( SQLException e ) {
//            throw new DBConnectException();
//        }
//
//    }
//}


//public class DBMesure extends DBDataMapper<Mesure> implements MesureRepository {
//    private final DBRepoObjectMapper<Mesure> referenceMapper;
//
//    public DBMesure(String connectionString) {
//        super(new DBConnector(connectionString), "Mesures");
//        this.referenceMapper = new DBRepoObjectMapper<>();
//    }
//
//    @Override
//    public int getMesureId(Mesure mesure) {
//        return referenceMapper.getObjRef(mesure);
//    }
//
//    @Override
//    public void saveMesure(Mesure entity) throws DBInsertException, DBConnectException {
//
//
//        try ( Connection connection = dbConnector.getConnection() ) {
//            try {
//                connection.setAutoCommit(false);
//                int id = saveAndGetReference(entity);
//                referenceMapper.addReference(entity, id);
//                connection.commit();
//            } catch ( SQLException e ) {
//                connection.rollback();
//                throw new DBInsertException();
//            } finally {
//                dbConnector.disconnect();
//            }
//        } catch ( SQLException e ) {
//            throw new DBConnectException();
//        }
//    }
//}




import org.junit.jupiter.api.Test;
import stas.thermometer.infrastructures.database.dbexceptions.DBConnectException;
import stas.thermometer.infrastructures.database.dbexceptions.DBInsertException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
public class DBFactoryTest {

    private final Mesure mesure = new Mesure("thermo", LocalDateTime.now(), "temperature","00.00°", 1.0);
    private final Alert alert = new Alert(0.5,1);
    private final String connectionString = "jdbc:derby:../StasThermometerDerby;user=demo;password=demo";


    @Test
    public void Should_Add_Alert_Without_Exception_When_saveAlerte() {
        DBAlert dbAlert = new DBAlert(connectionString);
        try {
            dbAlert.saveAlerte(alert);
        } catch ( DBInsertException e ) {
            fail("Insertion failed");
        } catch ( DBConnectException e ) {
            fail("Connection failed");
        }
    }
    @Test
    public void Should_throw_DBConnectException_When_saveAlerte_With_DBConnector_Throwing_SQLException(){

        DBAlert dbAlert = new DBAlert("not a good connection string");
        try {
            dbAlert.saveAlerte(alert);
            fail("Should throw DBConnectException");
        } catch ( DBInsertException e ) {
            fail("Insertion failed");
        } catch ( DBConnectException e ) {
            //ok
        }
    }

    @Test
    public void Should_throw_DBInsertException_When_saveAlerte_With_DBConnector_Throwing_SQLException() {

        DBAlert dbAlert = new DBAlert(connectionString);
        Alert alert = mock(Alert.class);
        try {
            dbAlert.saveAlerte(alert);
            fail("Should throw DBInsertException");
        } catch ( DBInsertException e ) {
            //ok
        } catch ( DBConnectException e ) {
            fail("Connection failed");
        }
    }

    @Test
    public void Should_Add_Mesure_Without_Exception_When_saveMesure() {
        DBMesure dbMesure = new DBMesure(connectionString);
        try {
            dbMesure.saveMesure(mesure);
        } catch ( DBInsertException e ) {
            fail("Insertion failed");
        } catch ( DBConnectException e ) {
            fail("Connection failed");
        }
    }

    @Test
    public void Should_throw_DBConnectException_When_saveMesure_With_DBConnector_Throwing_SQLException(){

        DBMesure dbMesure = new DBMesure("not a good connection string");
        try {
            dbMesure.saveMesure(mesure);
            fail("Should throw DBConnectException");
        } catch ( DBInsertException e ) {
            fail("Insertion failed");
        } catch ( DBConnectException e ) {
            //ok
        }
    }

    @Test
    public void Should_throw_DBInsertException_When_saveMesure_With_DBConnector_Throwing_SQLException(){

        DBMesure dbMesure = new DBMesure(connectionString);
        Mesure mesure = mock(Mesure.class);
        try {
            dbMesure.saveMesure(mesure);
            fail("Should throw DBInsertException");
        } catch ( DBInsertException e ) {
            //ok
        } catch ( DBConnectException e ) {
            fail("Connection failed");
        }
    }

    //test le getMesureId
    @Test
    public void Should_Return_idGenerate_When_getMesureId_AfterSaveMesure_WithoutMock() {
        DBMesure dbMesure = new DBMesure(connectionString);
        try {
            dbMesure.saveMesure(mesure);
            int idGenerate = dbMesure.getMesureId(mesure);
            //idgenerate doit etre plus grand que 1
            assert(idGenerate > 1);

        }  catch ( DBInsertException e ) {
            fail("Insertion failed");
        } catch ( DBConnectException e ) {
            fail("Connection failed");

        }
    }


}
