package stas.thermometer.domains;


import org.junit.jupiter.api.Test;
import stas.thermometer.domains.AggregatorHandler.AggregatorModeler;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AggregatorModelerTest {

    @Test
    public void Should_AdjustCorrectiveDelta_To_0_When_AdjustCorrectiveDeltaCalled() {
        // Arrange
        AggregatorModeler aggregatorModeler = new AggregatorModeler();
        double correctiveDelta = 0.0;

        // Act
        aggregatorModeler.adjustDelta(correctiveDelta);

        // Assert
        assertEquals(10.0, aggregatorModeler.execute(10.0));
        assertEquals(0, aggregatorModeler.getAlarmType());
    }

    @Test
    public void Should_AdjustCorrectiveDelta_To_10percent_When_AdjustCorrectiveDeltaCalled() {
        // Arrange
        AggregatorModeler aggregatorModeler = new AggregatorModeler();
        double correctiveDelta = 0.11;

        // Act
        aggregatorModeler.adjustDelta(correctiveDelta);

        // Assert
        assertEquals(11.1, aggregatorModeler.execute(10.0));
        assertEquals(1, aggregatorModeler.getAlarmType());
    }
}
