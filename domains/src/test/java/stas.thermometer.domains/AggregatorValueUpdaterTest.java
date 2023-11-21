package stas.thermometer.domains;


import org.junit.jupiter.api.Test;
import stas.thermometer.domains.aggregator.handler.AggregatorValueUpdater;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


public class AggregatorValueUpdaterTest {

    @Test
    public void Should_Return_False_when_Only_One_Call() {
        //given
        Probe probeMock = mock(Probe.class);
        when(probeMock.getMeasurement()).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,0)));
        AggregatorValueUpdater aggregatorValueUpdater = new AggregatorValueUpdater(probeMock);

        //when
        aggregatorValueUpdater.updater();

        //then
        assertFalse(aggregatorValueUpdater.updater());
    }

    @Test
    public void Should_Return_False_when_secondsBetween_IsLessThan2Second() {
        //given
        Probe probeMock = mock(Probe.class);
        when(probeMock.getMeasurement()).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,0)));
        AggregatorValueUpdater aggregatorValueUpdater = new AggregatorValueUpdater(probeMock);

        //when
        aggregatorValueUpdater.updater();
        when(probeMock.getMeasurement()).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,1)));
        //then
        assertFalse(aggregatorValueUpdater.updater());
    }

    @Test
    public void Should_Return_True_when_secondsBetween_IsMoreThan2Second() {
        //given
        Probe probeMock = mock(Probe.class);
        when(probeMock.getMeasurement()).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,0)));
        AggregatorValueUpdater aggregatorValueUpdater = new AggregatorValueUpdater(probeMock);

        //when
        aggregatorValueUpdater.updater();
        when(probeMock.getMeasurement()).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,3)));
        //then
        assertTrue(aggregatorValueUpdater.updater());
    }

    @Test
    public void Should_Return_AverageMeasurement_when_getAverageMeasurement_IsCalled() {
        //given
        Probe probeMock = mock(Probe.class);
        when(probeMock.getMeasurement()).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,0)));
        AggregatorValueUpdater aggregatorValueUpdater = new AggregatorValueUpdater(probeMock);

        //when
        aggregatorValueUpdater.updater();
        when(probeMock.getMeasurement()).thenReturn(new Measurement(2.0, LocalDateTime.of(2000, 3, 1, 0, 0,3)));
        aggregatorValueUpdater.updater();
        //then
        assertEquals(1.5, aggregatorValueUpdater.getAverageMeasurement());
    }

     @Test
    public void Should_Return_AggregatorMeasurementSimple_when_getMeasurementSimple_IsCalled() {
        //given
        Probe probeMock = mock(Probe.class);
        when(probeMock.getMeasurement()).thenReturn(new Measurement(1.0, LocalDateTime.of(2000, 3, 1, 0, 0,0)));
        AggregatorValueUpdater aggregatorValueUpdater = new AggregatorValueUpdater(probeMock);

        //when
        aggregatorValueUpdater.updater();
        when(probeMock.getMeasurement()).thenReturn(new Measurement(2.0, LocalDateTime.of(2000, 3, 1, 0, 0,3)));
        aggregatorValueUpdater.updater();
        //then
        assertEquals(1.5, aggregatorValueUpdater.getMeasurementSimple().value());
    }

}
