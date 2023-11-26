package stas.thermometer.domains;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import stas.thermometer.domains.aggregator.handler.AggregatorLogistical;
import stas.thermometer.domains.aggregator.handler.AggregatorValueUpdater;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class AggregatorLogisticalTest {

    @Mock
    AggregatorValueUpdater aggregatorValueUpdaterMock = mock(AggregatorValueUpdater.class);

    @Test
    public void Should_Return_False_when_update_IsCalled_One_time_and_aggregatorValueUpdater_IsFalse() {
        //given
        when(aggregatorValueUpdaterMock.updater()).thenReturn(false);
        AggregatorLogistical aggregatorLogistical = new AggregatorLogistical(aggregatorValueUpdaterMock, 0.5);

        //when
        boolean result = aggregatorLogistical.update();

        //then
        assertFalse(result);
        verify(aggregatorValueUpdaterMock, times(1)).updater();
        verify(aggregatorValueUpdaterMock, times(0)).getMeasurementSimple();
    }

    @Test
    public void Should_Return_True_when_update_IsCalled_Two_times_and_aggregatorValueUpdater_IsTrue() {
        //given
        when(aggregatorValueUpdaterMock.updater()).thenReturn(true);
        when(aggregatorValueUpdaterMock.getMeasurementSimple()).thenReturn(new Measurement(1.0, LocalDateTime.now()));
        AggregatorLogistical aggregatorLogistical = new AggregatorLogistical(aggregatorValueUpdaterMock, 0.5);

        //when
        boolean result = aggregatorLogistical.update();

        //then
        assertTrue(result);
        verify(aggregatorValueUpdaterMock, times(1)).updater();
        verify(aggregatorValueUpdaterMock, times(1)).getMeasurementSimple();
    }

    @Test
    public void Should_Return_1_when_getAlarmType_IsCalled_and_aggregatorAlarm_Is1() {
        //given
        when(aggregatorValueUpdaterMock.updater()).thenReturn(true);
        when(aggregatorValueUpdaterMock.getMeasurementSimple()).thenReturn(new Measurement(1.0, LocalDateTime.now()));
        AggregatorLogistical aggregatorLogistical = new AggregatorLogistical(aggregatorValueUpdaterMock, 0.5);

        //when
        aggregatorLogistical.update();
        aggregatorLogistical.adjustDelta(true);

        aggregatorLogistical.update();
        int result = aggregatorLogistical.getAlarmType();

        //then
        assertEquals(1, result);
        verify(aggregatorValueUpdaterMock, times(2)).updater();
        verify(aggregatorValueUpdaterMock, times(2)).getMeasurementSimple();
    }

    @Test
    public void Should_Return_MesurementMod_when_getMeasurementMod_IsCalled() {
        //given
        when(aggregatorValueUpdaterMock.updater()).thenReturn(true);
        when(aggregatorValueUpdaterMock.getMeasurementSimple()).thenReturn(new Measurement(1.0, LocalDateTime.now()));
        AggregatorLogistical aggregatorLogistical = new AggregatorLogistical(aggregatorValueUpdaterMock, 0.5);

        //when
        aggregatorLogistical.update();
        aggregatorLogistical.adjustDelta(false);

        aggregatorLogistical.update();
        Measurement result = aggregatorLogistical.getMeasurementMod();

        //then
        assertNotNull(result);
        assertEquals(0.5, result.value());
        verify(aggregatorValueUpdaterMock, times(2)).updater();
        verify(aggregatorValueUpdaterMock, times(2)).getMeasurementSimple();
    }

    @Test
    public void Should_Return_MesurementSimple_when_getMeasurementSimple_IsCalled() {
        //given
        when(aggregatorValueUpdaterMock.updater()).thenReturn(true);
        when(aggregatorValueUpdaterMock.getMeasurementSimple()).thenReturn(new Measurement(1.0, LocalDateTime.now()));
        AggregatorLogistical aggregatorLogistical = new AggregatorLogistical(aggregatorValueUpdaterMock, 0.5);

        //when
        aggregatorLogistical.update();
        aggregatorLogistical.adjustDelta(true);

        aggregatorLogistical.update();
        Measurement result = aggregatorLogistical.getMeasurementSimple();

        //then
        assertNotNull(result);
        assertEquals(1.0, result.value());
        verify(aggregatorValueUpdaterMock, times(2)).updater();
        verify(aggregatorValueUpdaterMock, times(3)).getMeasurementSimple();
    }


}
