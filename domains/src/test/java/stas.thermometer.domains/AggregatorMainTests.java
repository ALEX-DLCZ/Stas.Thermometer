package stas.thermometer.domains;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AggregatorMainTests {


    @Mock
    Probe probeMock = mock(Probe.class);


    @Test
    public void Should_Return_AggregatorName_when_getName_IsCalled() {
        //given
        when(probeMock.getName()).thenReturn("probeName");
        AggregatorMain aggregatorMain = new AggregatorMain(probeMock);
        //when
        String aggregatorName = aggregatorMain.getName();
        //then
        assertEquals("probeName", aggregatorName);
    }

    @Test
    public void Should_call1Time_updateAgregatedValues_when_updateAgregatedValues_IsCalled() {
        //given
        when(probeMock.getName()).thenReturn("probeName");
        AggregatorMain aggregatorMain = new AggregatorMain(probeMock);
        //when
        aggregatorMain.updateAgregatedValues();
        //then
        assertEquals(1, aggregatorMain.getmesurementSimple().value());
    }

    @Test
    public void Should_Return_AggregatorMeasurementMod_when_getmesurementMod_IsCalled() {

        //given
        LocalDateTime date = LocalDateTime.of(2000, 3, 1, 0, 0);
        when(probeMock.getMeasurement()).thenReturn(new Measurement(0.0, date));
        AggregatorMain aggregatorMain = new AggregatorMain(probeMock);

        //when
        aggregatorMain.updateAgregatedValues();
        Measurement measurementMod = aggregatorMain.getmesurementMod();

        //then
        assertNotNull(measurementMod);
        assertEquals(0.0, measurementMod.value());
        assertEquals(date, measurementMod.dateTime());
    }

    @Test
    public void Should_AggregatorMeasurementSimple_SameThanBefore_when_adjustDelta_IsCalled() {

        //given
        LocalDateTime date = LocalDateTime.of(2000, 3, 1, 0, 0);
        when(probeMock.getMeasurement()).thenReturn(new Measurement(10.0, date));
        AggregatorMain aggregatorMain = new AggregatorMain(probeMock);

        //when
        aggregatorMain.updateAgregatedValues();
        Measurement measurementSimple = aggregatorMain.getmesurementSimple();
        Measurement measurementMod = aggregatorMain.getmesurementMod();

        //then
        assertNotNull(measurementSimple);
        assertEquals(10.0, measurementSimple.value());
        assertEquals(date, measurementSimple.dateTime());
        assertNotEquals(measurementSimple.value(), measurementMod.value());
    }



}
