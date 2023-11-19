package stas.thermometer.domains;

import org.junit.jupiter.api.Test;
import stas.thermometer.domains.AggregatorHandler.AggregatorCorrective;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AggregatorCorrectiveTest {


    @Test
    public void Should_AdjustCorrectiveDelta_To_0_When_AdjustCorrectiveDeltaCalled() {
        // Arrange
        AggregatorCorrective aggregatorCorrective = new AggregatorCorrective();
        double correctiveDelta = 0.0;

        // Act
        aggregatorCorrective.adjustCorrectiveDelta(correctiveDelta);

        // Assert
        assertEquals(10.0, aggregatorCorrective.execute(10.0));
    }

    @Test
    public void Should_AdjustCorrectiveDelta_To_10percent_When_AdjustCorrectiveDeltaCalled() {
        // Arrange
        AggregatorCorrective aggregatorCorrective = new AggregatorCorrective();
        double correctiveDelta = 0.1;

        // Act
        aggregatorCorrective.adjustCorrectiveDelta(correctiveDelta);

        // Assert
        assertEquals(11.0, aggregatorCorrective.execute(10.0));
    }
    @Test
    public void Should_AdjustCorrectiveDelta_To_100percent_When_AdjustCorrectiveDeltaCalled() {
        // Arrange
        AggregatorCorrective aggregatorCorrective = new AggregatorCorrective();
        double correctiveDelta = 1.0;

        // Act
        aggregatorCorrective.adjustCorrectiveDelta(correctiveDelta);

        // Assert
        assertEquals(20.0, aggregatorCorrective.execute(10.0));
    }
    @Test
    public void Should_AdjustCorrectiveDelta_To_minus20percent_When_AdjustCorrectiveDeltaCalled() {
        // Arrange
        AggregatorCorrective aggregatorCorrective = new AggregatorCorrective();
        double correctiveDelta = -0.2;

        // Act
        aggregatorCorrective.adjustCorrectiveDelta(correctiveDelta);

        // Assert
        assertEquals(8.0, aggregatorCorrective.execute(10.0));
    }
}
