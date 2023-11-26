package stas.thermometer.presentations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import stas.thermometer.domains.Measurement;
import stas.thermometer.domains.ThermometerInterface;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class MainPresenterTest {


    @Mock
    MainViewInterface viewMock = mock(MainViewInterface.class);

    @Mock
    ThermometerInterface thermometerMock = mock(ThermometerInterface.class);
    @Mock
    AggregatorAccessor aggregatorAccessorMockTemperature = mock(AggregatorAccessor.class);
    @Mock
    AggregatorAccessor aggregatorAccessorMockHumidity = mock(AggregatorAccessor.class);

    @BeforeEach
    public void setUp() {

        when(thermometerMock.getAggregatorsAccessor()).thenReturn(List.of(aggregatorAccessorMockTemperature, aggregatorAccessorMockHumidity));
        when(aggregatorAccessorMockTemperature.getName()).thenReturn("temperature");
        when(aggregatorAccessorMockHumidity.getName()).thenReturn("humidity");

    }

    private final Map<String, String> format = Map.of(
            "temperature", "Température : %s °C",
            "humidity", "Humidité : %s %%"
    );



    @Test
    public void Should_call1Time_addSubscriber_when_MainPresenter_IsCreated() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        //then
        verify(aggregatorAccessorMockTemperature, times(1)).addSubscriber(any());
        verify(aggregatorAccessorMockHumidity, times(1)).addSubscriber(any());
    }
    @Test
    public void Should_call1Time_setPresenter_when_MainPresenter_IsCreated() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        //then
        verify(viewMock, times(1)).setPresenter(mainPresenter);
    }
    @Test
    public void Should_call1Time_getAggregatorsAccessor_when_MainPresenter_IsCreated() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        //then
        verify(thermometerMock, times(1)).getAggregatorsAccessor();
    }



    @Test
    public void Should_call1Time_printString_and_inputLoop_when_Start_IsCalled() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        //when
        mainPresenter.Start();
        //then
        verify(viewMock, times(1)).printString("Bienvenue dans le thermomètre");
        verify(viewMock, times(1)).inputLoop();
    }
    @Test
    public void Should_call1Time_notifyUpdate_when_onUpdate_IsCalled() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        //when
        mainPresenter.onUpdate();
        //then
        verify(thermometerMock, times(1)).notifyUpdate();
    }



    @Test
    public void Should_call1Time_printString_when_processingUserInput_IsCalled_with_h() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        //when
        mainPresenter.processingUserInput("h");
        //then
        verify(viewMock, times(1)).printString("h: help\nP: quit\nr: raise\nm: minimize\ns: change probe");
    }
    @Test
    public void Should_call1Time_printString_when_processingUserInput_IsCalled_with_q() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        //when
        mainPresenter.processingUserInput("q");
        //then
        verify(viewMock, times(1)).printString("Bye");
    }
    @Test
    public void Should_call1Time_printString_and_adjustDelta_when_processingUserInput_IsCalled_with_r() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        //when
        mainPresenter.processingUserInput("r");
        //then
        verify(viewMock, times(1)).printString("raise");
        verify(aggregatorAccessorMockTemperature, times(1)).adjustDelta(true);
        verify(aggregatorAccessorMockHumidity, times(0)).adjustDelta(true);
    }
    @Test
    public void Should_call1Time_printString_and_adjustDelta_when_processingUserInput_IsCalled_with_m() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        //when
        mainPresenter.processingUserInput("m");
        //then
        verify(viewMock, times(1)).printString("minimize");
        verify(aggregatorAccessorMockTemperature, times(1)).adjustDelta(false);
        verify(aggregatorAccessorMockHumidity, times(0)).adjustDelta(false);
    }
    @Test
    public void Should_call1Time_printString_when_processingUserInput_IsCalled_with_s() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        when(aggregatorAccessorMockHumidity.getName()).thenReturn("humidity");
        when(aggregatorAccessorMockTemperature.getName()).thenReturn("temperature");

        //when
        mainPresenter.processingUserInput("s");
        //then
        verify(viewMock, times(1)).printString("Changement de sonde\nSonde actuelle : humidity");
    }
    @Test
    public void Should_call1Time_printString_when_processingUserInput_IsCalled_with_s_and_raise_humidity() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        when(aggregatorAccessorMockHumidity.getName()).thenReturn("humidity");
        when(aggregatorAccessorMockTemperature.getName()).thenReturn("temperature");
        //when
        mainPresenter.processingUserInput("s");
        mainPresenter.processingUserInput("r");
        //then
        verify(viewMock, times(1)).printString("Changement de sonde\nSonde actuelle : humidity");
        verify(viewMock, times(1)).printString("raise");
        verify(aggregatorAccessorMockTemperature, times(0)).adjustDelta(true);
        verify(aggregatorAccessorMockHumidity, times(1)).adjustDelta(true);
    }
    @Test
    public void Should_call1Time_printString_when_processingUserInput_IsCalled_with_z() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);
        //when
        mainPresenter.processingUserInput("z");
        //then
        verify(viewMock, times(1)).printString("Commande inconnue");
    }

    @Test
    public void Should_call1Time_printString_when_updateAggregatorNotification_IsCalled() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);

        when(aggregatorAccessorMockHumidity.getName()).thenReturn("humidity");
        when(aggregatorAccessorMockTemperature.getName()).thenReturn("temperature");
        when(aggregatorAccessorMockTemperature.getAlarmType()).thenReturn(0);

        Measurement measurement = mock(Measurement.class);
        when(measurement.dateTime()).thenReturn(java.time.LocalDateTime.of(2021, 11, 11, 12, 12, 12));
        when(measurement.value()).thenReturn(12.3028943);
        when(aggregatorAccessorMockTemperature.getmesurementSimple()).thenReturn(measurement);
        when(aggregatorAccessorMockTemperature.getName()).thenReturn("temperature");
        when(aggregatorAccessorMockTemperature.getmesurementMod()).thenReturn(measurement);
        when(aggregatorAccessorMockTemperature.getmesurementMod().dateTime()).thenReturn(java.time.LocalDateTime.of(2021, 11, 11, 12, 12, 12));
        when(aggregatorAccessorMockTemperature.getmesurementMod().value()).thenReturn(12.3028943);

        //when
        mainPresenter.updateAggregatorNotification("temperature");
        //then
        verify(viewMock, times(1)).printString(anyString());
    }
    @Test
    public void Should_call1Time_printString_when_updateAggregatorNotification_IsCalled_with_alarmType1() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);

        when(aggregatorAccessorMockHumidity.getName()).thenReturn("humidity");
        when(aggregatorAccessorMockTemperature.getName()).thenReturn("temperature");
        when(aggregatorAccessorMockTemperature.getAlarmType()).thenReturn(1);

        Measurement measurement = mock(Measurement.class);
        when(measurement.dateTime()).thenReturn(java.time.LocalDateTime.of(2021, 11, 11, 12, 12, 12));
        when(measurement.value()).thenReturn(12.3028943);
        when(aggregatorAccessorMockTemperature.getmesurementSimple()).thenReturn(measurement);
        when(aggregatorAccessorMockTemperature.getName()).thenReturn("temperature");
        when(aggregatorAccessorMockTemperature.getmesurementMod()).thenReturn(measurement);
        when(aggregatorAccessorMockTemperature.getmesurementMod().dateTime()).thenReturn(java.time.LocalDateTime.of(2021, 11, 11, 12, 12, 12));
        when(aggregatorAccessorMockTemperature.getmesurementMod().value()).thenReturn(12.3028943);

        //when
        mainPresenter.updateAggregatorNotification("temperature");
        //then
        verify(viewMock, times(1)).printString(anyString());
    }
    @Test
    public void Should_Throw_RuntimeException_when_updateAggregatorNotification_IsCalled_with_unknownAggregator() {
        //given
        MainPresenter mainPresenter = new MainPresenter(viewMock, thermometerMock, format);

        when(aggregatorAccessorMockHumidity.getName()).thenReturn("humidity");
        when(aggregatorAccessorMockTemperature.getName()).thenReturn("temperature");
        when(aggregatorAccessorMockTemperature.getAlarmType()).thenReturn(1);

        Measurement measurement = mock(Measurement.class);
        when(measurement.dateTime()).thenReturn(java.time.LocalDateTime.of(2021, 11, 11, 12, 12, 12));
        when(measurement.value()).thenReturn(12.3028943);
        when(aggregatorAccessorMockTemperature.getmesurementSimple()).thenReturn(measurement);
        when(aggregatorAccessorMockTemperature.getName()).thenReturn("temperature");
        when(aggregatorAccessorMockTemperature.getmesurementMod()).thenReturn(measurement);
        when(aggregatorAccessorMockTemperature.getmesurementMod().dateTime()).thenReturn(java.time.LocalDateTime.of(2021, 11, 11, 12, 12, 12));
        when(aggregatorAccessorMockTemperature.getmesurementMod().value()).thenReturn(12.3028943);

        //when
        //then
        try {
            mainPresenter.updateAggregatorNotification("unknownAggregator");
        } catch (RuntimeException e) {
            //then
            verify(viewMock, times(0)).printString(anyString());
        }
    }

}
