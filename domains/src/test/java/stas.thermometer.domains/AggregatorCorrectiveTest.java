package stas.thermometer.domains;

import org.junit.jupiter.api.Test;
import stas.thermometer.domains.aggregator.handler.AggregatorCorrective;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AggregatorCorrectiveTest {

    @Test
    public void Should_Return_10_When_TempIs10_And_CorrectiveDeltaIs0() {
        // Arrange
        AggregatorCorrective aggregatorCorrective = new AggregatorCorrective(0.0);
        double temp = 10.0;

        // Act
        double result = aggregatorCorrective.execute(temp);

        // Assert
        assertEquals(10.0, result);
    }

    @Test
    public void Should_Return_10_When_TempIs10_And_CorrectiveDelta_has_not_been_adjusted() {
        // Arrange
        AggregatorCorrective aggregatorCorrective = new AggregatorCorrective(1.0);
        double temp = 10.0;

        // Act
        double result = aggregatorCorrective.execute(temp);

        // Assert
        assertEquals(10.0, result);
    }

}
