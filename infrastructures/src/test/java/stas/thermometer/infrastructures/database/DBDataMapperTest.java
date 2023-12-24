package stas.thermometer.infrastructures.database;


//public class DBDataMapperTest<T> implements DataMapper<T> {
//    protected DBConnector dbConnector;
//    private final String tableName;
//
//    public DBDataMapperTest(DBConnector dbConnector, String tableName) {
//        this.dbConnector = dbConnector;
//        this.tableName = tableName;
//    }
//
//    @Override
//    public int saveAndGetReference(T entity) throws DBInsertException, DBConnectException {
//        try {
//            String insertQuery = buildInsertQuery(entity);
//            try ( PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS) ) {
//                setStatementParameters(preparedStatement, entity);
//                preparedStatement.execute();
//                try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys() ) {
//                    if ( generatedKeys.next() ) {
//                        return generatedKeys.getInt(1);
//                    }
//                    else {
//                        throw new SQLException("No keys generated after insertion.");
//                    }
//                }
//            }
//        } catch ( SQLException | IllegalAccessException e ) {
//            throw new DBConnectException();
//        }
//    }
//
//    private String buildInsertQuery(T entity) {
//        QueryBuilder<T> queryBuilder = new QueryBuilder<>();
//        return queryBuilder.buildInsertQuery(entity, tableName);
//    }
//
//    private void setStatementParameters(PreparedStatement preparedStatement, T entity) throws SQLException, IllegalAccessException {
//        Field[] fields = entity.getClass().getDeclaredFields();
//        int parameterIndex = 1;
//        for ( Field field : fields ) {
//            field.setAccessible(true);
//            Object columnValue = field.get(entity);
//            preparedStatement.setObject(parameterIndex++, columnValue);
//        }
//    }
//}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import stas.thermometer.infrastructures.database.dbexceptions.DBConnectException;
import stas.thermometer.infrastructures.database.dbexceptions.DBInsertException;

import java.sql.*;
import java.time.LocalDateTime;

public class DBDataMapperTest {


//user = demo password = demo
    private static final String connectionString = "jdbc:derby:../StasThermometerDerby;user=demo;password=demo";
    //mocked DbConnector
    private final DBConnector dbConnectorMock = mock(DBConnector.class);
    private final Mesure mesure = new Mesure("thermometerName", LocalDateTime.now(), "type", "00.00°", 1.0);


    //test forcé sans mocl
    @Test
    public void Should_Return_idGenerate_When_SaveAndGetReference_WithoutMock() {
        DBConnector dbConnector = new DBConnector(connectionString);
        DBDataMapper<Mesure> dbDataMapper = new DBDataMapper<>(dbConnector, "Mesures");
        try {
            int idGenerate = dbDataMapper.saveAndGetReference(mesure);
            //idgenerate doit etre plus grand que 1
            assert(idGenerate > 1);

        }  catch ( DBInsertException e ) {
            fail("Insertion failed");
        } catch ( DBConnectException e ) {
            fail("Connection failed");

        }
    }


    @Test
    public void Should_Add_MesureAndAlert_Without_Exception_When_SaveAndGetReference2Time(){
        DBConnector dbConnector = new DBConnector(connectionString);
        DBDataMapper<Mesure> dbDataMapper = new DBDataMapper<>(dbConnector, "Mesures");
        DBDataMapper<Alert> dbDataMapperAlert = new DBDataMapper<>(dbConnector, "Alerts");
        try {
            int idGenerate = dbDataMapper.saveAndGetReference(mesure);
            //idgenerate doit etre plus grand que 1
            assert(idGenerate > 1);
            Alert alert = new Alert(1.0, idGenerate);

            int idGenerateAlert = dbDataMapperAlert.saveAndGetReference(alert);
            assert(idGenerateAlert > 1);
        }  catch ( DBInsertException e ) {
            fail("Insertion failed");
        } catch ( DBConnectException e ) {
            fail("Connection failed");

        }
    }

    @Test
    public void Should_Throw_DBConnectException_When_SaveAndGetReference_With_DBConnector_Throwing_SQLException() throws SQLException {
        DBConnector dbConnectorMock = mock(DBConnector.class);
        when(dbConnectorMock.getConnection()).thenThrow(SQLException.class);
        DBDataMapper<Mesure> dbDataMapper = new DBDataMapper<>(dbConnectorMock, "MESURES");
        try {
            dbDataMapper.saveAndGetReference(mesure);
            fail("Should throw DBConnectException");
        } catch ( DBInsertException | DBConnectException e ) {
            // OK
        }
    }

    //test happy path
    @Test
    public void Should_Return_idGenerate_When_SaveAndGetReference_With_Mock() throws SQLException {
        Connection connectionMock = mock(Connection.class);
        when(dbConnectorMock.getConnection()).thenReturn(connectionMock);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        ResultSet resultSetMock = mock(ResultSet.class);
        when(dbConnectorMock.getConnection().prepareStatement(anyString(), anyInt())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(1);
        when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt(1)).thenReturn(1);

        DBDataMapper<Mesure> dbDataMapper = new DBDataMapper<>(dbConnectorMock, "MESURES");
        int idGenerate = 0;
        try {
            idGenerate = dbDataMapper.saveAndGetReference(mesure);
        } catch ( DBInsertException e ) {
            throw new RuntimeException(e);
        } catch ( DBConnectException e ) {
            throw new RuntimeException(e);
        }
        assert(idGenerate == 1);
    }



}
