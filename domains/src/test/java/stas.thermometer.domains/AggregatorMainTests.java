package stas.thermometer.domains;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


public class AggregatorMainTests {


    @Mock
    Probe probeMock = mock(Probe.class);


    @Test
    public void Should_Return_AggregatorName_when_getName_IsCalled() {
        //given
        AggregatorMain aggregatorMain = new AggregatorMain("probeName",probeMock, 0.5);
        //when
        String aggregatorName = aggregatorMain.getName();
        //then
        assertEquals("probeName", aggregatorName);
    }

    @Test
    public void Should_call1Time_updateAgregatedValues_when_updateAgregatedValues_IsCalled() {
        //given
        AggregatorMain aggregatorMain = new AggregatorMain("probeName",probeMock, 0.5);


        //when

        when(probeMock.generateMeasurement(any(LocalDateTime.class))).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,0)));
        aggregatorMain.updateAgregatedValues();
        when(probeMock.generateMeasurement(any(LocalDateTime.class))).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,3)));
        aggregatorMain.updateAgregatedValues();


        //then
        assertEquals(1.0, aggregatorMain.getmesurementSimple().value());
    }

//    @Test
//    public void Should_Return_AggregatorMeasurementMod_when_getmesurementMod_IsCalled() {
//
//        //given
//        AggregatorMain aggregatorMain = new AggregatorMain(probeMock);
//
//        //when
//        when(probeMock.generateMeasurement(date)).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,0)));
//        aggregatorMain.updateAgregatedValues();
//        when(probeMock.generateMeasurement(date)).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,3)));
//        aggregatorMain.updateAgregatedValues();
//
//        Measurement measurementMod = aggregatorMain.getmesurementMod();
//
//        //then
//        assertNotNull(measurementMod);
//        assertEquals(1.0, measurementMod.value());
//        verify(probeMock, times(2)).getMeasurement();
//
//    }

    @Test
    public void Should_AggregatorMeasurementSimple_SameThanBefore_when_adjustDelta_IsCalled() {

        //given
        AggregatorMain aggregatorMain = new AggregatorMain("probeName",probeMock, 0.5);

        //when
        when(probeMock.generateMeasurement(any(LocalDateTime.class))).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,0)));
        aggregatorMain.updateAgregatedValues();
        aggregatorMain.adjustDelta(true);
        when(probeMock.generateMeasurement(any(LocalDateTime.class))).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,3)));
        aggregatorMain.updateAgregatedValues();

        aggregatorMain.updateAgregatedValues();
        Measurement measurementSimple = aggregatorMain.getmesurementSimple();
        Measurement measurementMod = aggregatorMain.getmesurementMod();

        //then
        assertNotNull(measurementSimple);
        assertEquals(1.0, measurementSimple.value());
        assertEquals(1.5, measurementMod.value());
        assertNotEquals(measurementSimple.value(), measurementMod.value());
    }

    @Test
    public void Should_Raise_Delta_when_adjustDelta_With_positivDouble_IsCalled() {

        //given
        AggregatorMain aggregatorMain = new AggregatorMain("probeName",probeMock, 0.5);

        //when
        when(probeMock.generateMeasurement(any(LocalDateTime.class))).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,0)));
        aggregatorMain.updateAgregatedValues();
        aggregatorMain.adjustDelta(true);
        when(probeMock.generateMeasurement(any(LocalDateTime.class))).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,3)));
        aggregatorMain.updateAgregatedValues();

        aggregatorMain.updateAgregatedValues();
        Measurement measurementSimple = aggregatorMain.getmesurementSimple();
        Measurement measurementMod = aggregatorMain.getmesurementMod();

        //then
        assertNotNull(measurementMod);
        assertEquals(1.5, measurementMod.value());
        assertTrue(measurementSimple.value() < measurementMod.value());
    }

    @Test
    public void Should_Return_AggregatorAlarmType_when_getAlarmType_IsCalled() {

        //given
        AggregatorMain aggregatorMain = new AggregatorMain("probeName",probeMock, 0.5);

        //when
        when(probeMock.generateMeasurement(any(LocalDateTime.class))).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,0)));
        aggregatorMain.updateAgregatedValues();
        when(probeMock.generateMeasurement(any(LocalDateTime.class))).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,3)));
        aggregatorMain.updateAgregatedValues();

        int alarmType = aggregatorMain.getAlarmType();

        //then
        assertEquals(0, alarmType);
    }


    @Test
    public void Should_ManageDPObserveur_and_Notify_Subscribers_when_updateAgregatedValues_IsCalled() {


        //given
        AggregatorMain aggregatorMain = new AggregatorMain("probeName",probeMock, 0.5);
        AggregatorSubscriber aggregatorSubscriberMockToRemove = mock(AggregatorSubscriber.class);
        AggregatorSubscriber aggregatorSubscriberMockToAdd = mock(AggregatorSubscriber.class);
        AggregatorSubscriber aggregatorSubscriberMockToAdd2 = mock(AggregatorSubscriber.class);
        //Act
        aggregatorMain.addSubscriber(aggregatorSubscriberMockToRemove);
        aggregatorMain.addSubscriber(aggregatorSubscriberMockToAdd);
        aggregatorMain.addSubscriber(aggregatorSubscriberMockToAdd2);
        aggregatorMain.removeSubscriber(aggregatorSubscriberMockToRemove);


        //when
        when(probeMock.generateMeasurement(any(LocalDateTime.class))).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,0)));
        aggregatorMain.updateAgregatedValues();
        when(probeMock.generateMeasurement(any(LocalDateTime.class))).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,3)));
        aggregatorMain.updateAgregatedValues();


        //then
        verify(aggregatorSubscriberMockToRemove, times(0)).updateAggregatorNotification("probeName");
        verify(aggregatorSubscriberMockToAdd, times(1)).updateAggregatorNotification("probeName");
        verify(aggregatorSubscriberMockToAdd2, times(1)).updateAggregatorNotification("probeName");
    }



}
