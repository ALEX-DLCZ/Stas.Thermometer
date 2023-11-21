package stas.thermometer.domains;



import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import stas.thermometer.domains.AggregatorHandler.AggregatorLogistical;
import stas.thermometer.domains.AggregatorHandler.AggregatorValueUpdater;


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
        AggregatorLogistical aggregatorLogistical = new AggregatorLogistical(aggregatorValueUpdaterMock);

        //when
        boolean result = aggregatorLogistical.update();

        //then
        assertFalse(result);
        verify(aggregatorValueUpdaterMock, times(1)).updater();
        verify(aggregatorValueUpdaterMock, times(0)).getAverageMeasurement();
    }

    @Test
    public void Should_Return_True_when_update_IsCalled_Two_times_and_aggregatorValueUpdater_IsTrue() {
        //given
        when(aggregatorValueUpdaterMock.updater()).thenReturn(true);
        when(aggregatorValueUpdaterMock.getAverageMeasurement()).thenReturn(1.0);
        AggregatorLogistical aggregatorLogistical = new AggregatorLogistical(aggregatorValueUpdaterMock);

        //when
        boolean result = aggregatorLogistical.update();

        //then
        assertTrue(result);
        verify(aggregatorValueUpdaterMock, times(1)).updater();
        verify(aggregatorValueUpdaterMock, times(1)).getAverageMeasurement();
    }

    @Test
    public void Should_Return_1_when_getAlarmType_IsCalled_and_aggregatorAlarm_Is1() {
        //given
        when(aggregatorValueUpdaterMock.updater()).thenReturn(true);
        when(aggregatorValueUpdaterMock.getAverageMeasurement()).thenReturn(1.0);
        AggregatorLogistical aggregatorLogistical = new AggregatorLogistical(aggregatorValueUpdaterMock);

        //when
        aggregatorLogistical.update();
        aggregatorLogistical.adjustDelta(10.0);

        aggregatorLogistical.update();
        int result = aggregatorLogistical.getAlarmType();

        //then
        assertEquals(1, result);
        verify(aggregatorValueUpdaterMock, times(2)).updater();
        verify(aggregatorValueUpdaterMock, times(2)).getAverageMeasurement();
    }

    @Test
    public void Should_Return_MesurementMod_when_getMeasurementMod_IsCalled() {
        //given
        when(aggregatorValueUpdaterMock.updater()).thenReturn(true);
        when(aggregatorValueUpdaterMock.getAverageMeasurement()).thenReturn(1.0);
        AggregatorLogistical aggregatorLogistical = new AggregatorLogistical(aggregatorValueUpdaterMock);

        //when
        aggregatorLogistical.update();
        aggregatorLogistical.adjustDelta(10.0);

        aggregatorLogistical.update();
        Measurement result = aggregatorLogistical.getMeasurementMod();

        //then
        assertNotNull(result);
        assertEquals(11.0, result.value());
        verify(aggregatorValueUpdaterMock, times(2)).updater();
        verify(aggregatorValueUpdaterMock, times(2)).getAverageMeasurement();
    }


    @Test
    public void Should_Return_MesurementSimple_when_getMeasurementSimple_IsCalled() {
        //given
        when(aggregatorValueUpdaterMock.updater()).thenReturn(true);
        when(aggregatorValueUpdaterMock.getAverageMeasurement()).thenReturn(1.0);
        when(aggregatorValueUpdaterMock.getMeasurementSimple()).thenReturn(new Measurement(1.0, LocalDateTime.now()));
        AggregatorLogistical aggregatorLogistical = new AggregatorLogistical(aggregatorValueUpdaterMock);

        //when
        aggregatorLogistical.update();
        aggregatorLogistical.adjustDelta(10.0);

        aggregatorLogistical.update();
        Measurement result = aggregatorLogistical.getMeasurementSimple();

        //then
        assertNotNull(result);
        assertEquals(1.0, result.value());
        verify(aggregatorValueUpdaterMock, times(2)).updater();
        verify(aggregatorValueUpdaterMock, times(2)).getAverageMeasurement();
    }


}
