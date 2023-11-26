package stas.thermometer.presentations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import stas.thermometer.domains.Measurement;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import stas.thermometer.presentations.messages.MsgNotifOrganization;

import java.util.Map;

public class MsgNotifOrganizationTest {


    /*
    public class MsgNotifOrganization {
    private final MessageGenerator currentHandler;
    private final MessageGenerator alertHandler;

    public MsgNotifOrganization(Map<String, String> format) {
        this.currentHandler = new MsgCurrentHandler(format);
        this.alertHandler = new MsgAlertHandler();
    }

    public String getMsgCurrent(AggregatorAccessor aggregatorAccessorCible) {
        return currentHandler.getMessage(aggregatorAccessorCible);
    }

    public String getMsgAlert(AggregatorAccessor aggregatorAccessorCible) {
        return alertHandler.getMessage(aggregatorAccessorCible) + "\n" + currentHandler.getMessage(aggregatorAccessorCible);

    }
}
     */


    @Mock
    AggregatorAccessor aggregatorAccessorCibleMsgCurrent = mock(AggregatorAccessor.class);

    @Mock
    AggregatorAccessor aggregatorAccessorCibleMsgAlert = mock(AggregatorAccessor.class);

    @BeforeEach
    void setUp() {
        //aggregatorAccessorCibleMsgCurrent = mock(AggregatorAccessor.class);
        //aggregatorAccessorCibleMsgAlert = mock(AggregatorAccessor.class);
        Measurement measurement = mock(Measurement.class);
        when(measurement.dateTime()).thenReturn(java.time.LocalDateTime.of(2021, 11, 11, 12, 12, 12));
        when(measurement.value()).thenReturn(12.3028943);
        when(aggregatorAccessorCibleMsgCurrent.getmesurementSimple()).thenReturn(measurement);
        when(aggregatorAccessorCibleMsgCurrent.getName()).thenReturn("temperature");
        when(aggregatorAccessorCibleMsgCurrent.getmesurementMod()).thenReturn(measurement);
        when(aggregatorAccessorCibleMsgCurrent.getmesurementMod().dateTime()).thenReturn(java.time.LocalDateTime.of(2021, 11, 11, 12, 12, 12));
        when(aggregatorAccessorCibleMsgCurrent.getmesurementMod().value()).thenReturn(12.3028943);
        when(aggregatorAccessorCibleMsgAlert.getName()).thenReturn("temperature");
        when(aggregatorAccessorCibleMsgAlert.getmesurementSimple()).thenReturn(measurement);
        when(aggregatorAccessorCibleMsgAlert.getmesurementMod()).thenReturn(measurement);
        when(aggregatorAccessorCibleMsgAlert.getmesurementMod().dateTime()).thenReturn(java.time.LocalDateTime.of(2021, 11, 11, 12, 12, 12));
        when(aggregatorAccessorCibleMsgAlert.getmesurementMod().value()).thenReturn(12.3028943);

    }


    private final Map<String, String> format = Map.of(
            "datetime", "dd/MM/YYYY à HH :mm :ss",
            "temperature", "00.00°",
            "humidity", "00%"
    );


    private final Map<String, String> formatOther = Map.of(
            "datetime", "dd/MM/YYYY à HH :mm :ss",
            "temperature", "00.00°",
            "humidity", "00%"
    );

    @Test
    public void Should_Return_MsgCurrent_When_AggregatorAccessorCible_Is_Temperature() {

        // Given
        MsgNotifOrganization msgNotifOrganization = new MsgNotifOrganization(format);
        // When
        String result = msgNotifOrganization.getMsgCurrent(aggregatorAccessorCibleMsgCurrent);
        // Then
        //vérifie que la chaines de charactere contient bien 12.30
        assert (result.contains("12,30"));
        //vérifie que la chaines de charactere commence bien par 11/11/2021
        assert (result.startsWith("11/11/2021"));
    }

    @Test
    public void Should_Return_MsgAlert_When_AggregatorAccessorCible_Is_Temperature() {

        // Given
        MsgNotifOrganization msgNotifOrganization = new MsgNotifOrganization(format);
        when(aggregatorAccessorCibleMsgAlert.getAlarmType()).thenReturn(1);
        // When
        String result = msgNotifOrganization.getMsgAlert(aggregatorAccessorCibleMsgAlert);
        // Then
        assert (result.contains("12,30"));
        assert (result.startsWith("--TROP CHAUD--"));
    }

    @Test
    public void Should_Return_MsgCurrent_When_AggregatorAccessorCible_Is_Humidity() {

        // Given
        MsgNotifOrganization msgNotifOrganization = new MsgNotifOrganization(format);
        when(aggregatorAccessorCibleMsgCurrent.getName()).thenReturn("humidity");
        // When
        String result = msgNotifOrganization.getMsgCurrent(aggregatorAccessorCibleMsgCurrent);
        // Then
        assert (result.contains("1230"));
        assert (result.startsWith("11/11/2021"));
    }

    @Test
    public void Should_Return_MsgAlert_When_AggregatorAccessorCible_Is_Humidity() {

        // Given
        MsgNotifOrganization msgNotifOrganization = new MsgNotifOrganization(format);
        when(aggregatorAccessorCibleMsgAlert.getName()).thenReturn("humidity");
        when(aggregatorAccessorCibleMsgAlert.getAlarmType()).thenReturn(1);
        // When
        String result = msgNotifOrganization.getMsgAlert(aggregatorAccessorCibleMsgAlert);
        // Then
        assert (result.contains("1230"));
        assert (result.startsWith("--TROP HUMIDE--"));
    }



}
