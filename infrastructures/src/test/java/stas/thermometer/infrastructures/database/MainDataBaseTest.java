package stas.thermometer.infrastructures.database;



//public class MainDataBase {
//
//    private final List<AggregatorAccessor> aggregatorAccessors;
//    private final String thermometerName;
//    private final MesureRepository mesureRepository;
//    private final AlertRepository alertRepository;
//    private final Map<String, String> formatMap;
//
//    private static final Logger LOG = LogManager.getLogger("stas thermometre");
//
//    public MainDataBase(String connectionString, ThermometerInterface thermometerInterface, Map<String, String> formatMap) {
//        this.aggregatorAccessors = thermometerInterface.getAggregatorsAccessor();
//        this.thermometerName = thermometerInterface.getThermometerName();
//        this.formatMap = formatMap;
//
//        for ( AggregatorAccessor aggregatorAccessor : aggregatorAccessors ) {
//            aggregatorAccessor.addSubscriber(this::updateAggregatorNotification);
//        }
//
//        this.mesureRepository = new DBMesure(connectionString);
//        this.alertRepository = new DBAlert(connectionString);
//
//
//    }
//
//    private void updateAggregatorNotification(String aggregatorName) {
//        AggregatorAccessor aggregatorAccessor = this.aggregatorAccessors.stream().filter(aggregatorAccessor1 -> aggregatorAccessor1.getName().equals(aggregatorName)).findFirst().get();
//
//        Mesure mesure = new Mesure(this.thermometerName, aggregatorAccessor.getmesurementMod().dateTime(), aggregatorName, formatMap.get(aggregatorName), aggregatorAccessor.getmesurementMod().value());
//
//        try {
//            mesureRepository.saveMesure(mesure);
//            if ( aggregatorAccessor.getAlarmType() != 0 ) {
//                alertRepository.saveAlerte(new Alert(aggregatorAccessor.getmesurementSimple().value(), mesureRepository.getMesureId(mesure)));
//            }
//        } catch ( DBInsertException | DBConnectException e ) {
//            LOG.error(e.getMessage());
//        }
//    }
//
//}


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stas.thermometer.domains.AggregatorMain;
import stas.thermometer.domains.Measurement;
import stas.thermometer.domains.ThermometerInterface;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class MainDataBaseTest {

    private String connectionString = "jdbc:derby:../StasThermometerDerby";
    private ThermometerInterface thermometerInterfaceMock = mock(ThermometerInterface.class);
    private Map<String, String> formatMapMock = mock(Map.class);

    private AggregatorMain aggregatorMainMock = mock(AggregatorMain.class);

    @BeforeEach
    public void setUp() {
        when(thermometerInterfaceMock.getThermometerName()).thenReturn("thermo");
        when(aggregatorMainMock.getName()).thenReturn("temperature");
        when(aggregatorMainMock.getmesurementMod()).thenReturn(new Measurement(1.0, LocalDateTime.now()));
        when(aggregatorMainMock.getmesurementSimple()).thenReturn(new Measurement(1.0, LocalDateTime.now()));
        when(aggregatorMainMock.getAlarmType()).thenReturn(1);
//        when (aggregatorMainMock.updateAgregatedValues()).thenCallRealMethod();

        when(thermometerInterfaceMock.getAggregatorsAccessor()).thenReturn(List.of(aggregatorMainMock));
    }


    @Test
    public void Should_Create_MainDataBase_Without_Exception() {
        new MainDataBase(connectionString, thermometerInterfaceMock, formatMapMock);
        verify(thermometerInterfaceMock, times(1)).getThermometerName();
        verify(thermometerInterfaceMock, times(1)).getAggregatorsAccessor();

    }


    //private methode
//    @Test
//    public void Should_Call_SaveMesure_When_Aggregator_Notification() {
//        MainDataBase mainDataBase = new MainDataBase(connectionString, thermometerInterfaceMock, formatMapMock);
//        mainDataBase.updateAggregatorNotification("temperature");
//        verify(aggregatorMainMock, times(1)).getmesurementMod();
//        verify(aggregatorMainMock, times(1)).getmesurementSimple();
//        verify(aggregatorMainMock, times(1)).getAlarmType();
//    }


}
