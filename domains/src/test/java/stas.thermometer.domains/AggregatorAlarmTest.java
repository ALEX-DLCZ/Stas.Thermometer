package stas.thermometer.domains;

import org.junit.jupiter.api.Test;
import stas.thermometer.domains.AggregatorHandler.AggregatorAlarm;

import static org.junit.jupiter.api.Assertions.assertEquals;




public class AggregatorAlarmTest {

    @Test
    public void Should_Return_0_When_TempIs10_And_ModTempIs10() {
        // Arrange
        AggregatorAlarm aggregatorAlarm = new AggregatorAlarm();
        double temp = 10.0;
        double modTemp = 10.0;

        // Act
        aggregatorAlarm.execute(temp, modTemp);

        // Assert
        assertEquals(0, aggregatorAlarm.getAlarmType());
    }

    @Test
    public void Should_Return_1_When_TempIs10_And_ModTempIs11() {
        // Arrange
        AggregatorAlarm aggregatorAlarm = new AggregatorAlarm();
        double temp = 10.0;
        double modTemp = 11.001;

        // Act
        aggregatorAlarm.execute(temp, modTemp);

        // Assert
        assertEquals(1, aggregatorAlarm.getAlarmType());
    }
    @Test
    public void Should_Return_minus1_When_TempIs10_And_ModTempIs8() {
        // Arrange
        AggregatorAlarm aggregatorAlarm = new AggregatorAlarm();
        double temp = 10.0;
        double modTemp = 8.999;

        // Act
        aggregatorAlarm.execute(temp, modTemp);

        // Assert
        assertEquals(-1, aggregatorAlarm.getAlarmType());
    }
}
