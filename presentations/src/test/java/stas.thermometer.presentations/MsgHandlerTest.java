package stas.thermometer.presentations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import stas.thermometer.domains.Measurement;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;
import stas.thermometer.presentations.messages.MsgAlertHandler;
import stas.thermometer.presentations.messages.MsgCurrentHandler;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MsgHandlerTest {

    @Mock
    AggregatorAccessor aggregatorAccessorCibleMsgCurrent = mock(AggregatorAccessor.class);

    @Mock
    AggregatorAccessor aggregatorAccessorCibleMsgAlert = mock(AggregatorAccessor.class);

    @BeforeEach
    void setUp() {
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

    @Test
    public void Should_Return_Message_when_getMessage_IsCalled() {
        //given
        Map<String, String> format = Map.of("datetime", "dd/MM/YYYY à HH :mm :ss", "temperature", "00.00°", "humidity", "00%");
        MsgCurrentHandler msgCurrentHandler = new MsgCurrentHandler(format);

        //when
        String result = msgCurrentHandler.getMessage(aggregatorAccessorCibleMsgCurrent);

        //then
        assert (result.contains("12,30"));
        assert (result.startsWith("11/11/2021"));
    }

    @Test
    public void Should_Return_Message_TOHOT_when_getMessage_AlertType_Is1() {
        //given
        MsgAlertHandler msgCurrentHandler = new MsgAlertHandler();
        when(aggregatorAccessorCibleMsgAlert.getmesurementSimple()).thenReturn(new Measurement(1.0, java.time.LocalDateTime.of(2000, 3, 1, 0, 0, 0)));
        when(aggregatorAccessorCibleMsgAlert.getAlarmType()).thenReturn(1);
        //when
        String result = msgCurrentHandler.getMessage(aggregatorAccessorCibleMsgAlert);
        //then
        assert (result.contains("--TROP CHAUD--"));
    }

    @Test
    public void Should_Return_Message_TOCOLD_when_getMessage_AlertType_IsMinus1() {
        //given
        MsgAlertHandler msgCurrentHandler = new MsgAlertHandler();
        when(aggregatorAccessorCibleMsgAlert.getmesurementSimple()).thenReturn(new Measurement(1.0, java.time.LocalDateTime.of(2000, 3, 1, 0, 0, 0)));
        when(aggregatorAccessorCibleMsgAlert.getAlarmType()).thenReturn(-1);
        //when
        String result = msgCurrentHandler.getMessage(aggregatorAccessorCibleMsgAlert);
        //then
        assert (result.contains("--TROP FROID--"));
    }

    @Test
    public void Should_Return_Message_TOHUMID_when_getMessage_AlertType_Is1() {
        //given
        MsgAlertHandler msgCurrentHandler = new MsgAlertHandler();
        when(aggregatorAccessorCibleMsgAlert.getmesurementSimple()).thenReturn(new Measurement(1.0, java.time.LocalDateTime.of(2000, 3, 1, 0, 0, 0)));
        when(aggregatorAccessorCibleMsgAlert.getAlarmType()).thenReturn(1);
        when(aggregatorAccessorCibleMsgAlert.getName()).thenReturn("humidity");
        //when
        String result = msgCurrentHandler.getMessage(aggregatorAccessorCibleMsgAlert);
        //then
        assert (result.contains("--TROP HUMIDE--"));
    }

    @Test
    public void Should_Return_ERROR_MESSAGE_when_getMessage_cannot_find_type_From_Aggregator() {
        //given
        Map<String, String> format = Map.of("datetime", "dd/MM/YYYY à HH :mm :ss", "temperature", "00.00°", "humidity", "00%");
        MsgCurrentHandler msgCurrentHandler = new MsgCurrentHandler(format);
        when(aggregatorAccessorCibleMsgCurrent.getName()).thenReturn("typenonconform");
        //when
        String result = msgCurrentHandler.getMessage(aggregatorAccessorCibleMsgCurrent);
        //then
        assert (result.contains("ERROR CURRENT TYPE"));

    }

    @Test
    public void Should_Return_ERROR_MESSAGE_when_getMessage_cannot_find_type_From_Format() {
        //given
        Map<String, String> format = Map.of("datetime", "dd/MM/YYYY à HH :mm :ss", "pasBonTempérature", "", "humidity", "00%");
        MsgCurrentHandler msgCurrentHandler = new MsgCurrentHandler(format);
        when(aggregatorAccessorCibleMsgCurrent.getName()).thenReturn("temperature");
        //when
        String result = msgCurrentHandler.getMessage(aggregatorAccessorCibleMsgCurrent);
        //then
        assert (result.contains("ERROR CURRENT TYPE"));

    }

    @Test
    public void Should_Return_ERROR_MESSAGE_when_getMessage_cannot_find_type_From_Aggregator_ALERT() {
        //given

        MsgAlertHandler msgCurrentHandler = new MsgAlertHandler();

        when(aggregatorAccessorCibleMsgAlert.getName()).thenReturn("typenonconform");
        //when
        String result = msgCurrentHandler.getMessage(aggregatorAccessorCibleMsgAlert);
        //then
        assert (result.contains("ERROR ALERT TYPE"));

    }

    @Test
    public void Should_Return_ERROR_MESSAGE_when_getMessage_cannot_find_type_From_Format_ALERT() {
        //given
        Map<String, String> format = Map.of("datetime", "dd/MM/YYYY à HH :mm :ss", "pasBonTempérature", "", "humidity", "00%");
        MsgAlertHandler msgCurrentHandler = new MsgAlertHandler();
        when(aggregatorAccessorCibleMsgAlert.getName()).thenReturn("temperature");
        when(aggregatorAccessorCibleMsgAlert.getAlarmType()).thenReturn(0);
        //when
        String result = msgCurrentHandler.getMessage(aggregatorAccessorCibleMsgAlert);
        //then
        assert (result.contains("ERROR ALERT TYPE"));
    }
}
