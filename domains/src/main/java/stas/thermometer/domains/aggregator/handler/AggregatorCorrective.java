package stas.thermometer.domains.aggregator.handler;

public class AggregatorCorrective {
    private double correctiveDelta = 0.0;
    private final double delta;

    public AggregatorCorrective(double delta) {
        this.delta = delta;
    }

    public void adjustCorrectiveDelta(Boolean isRise) {
        if ( isRise ) {
            this.correctiveDelta += delta;
        }
        else {
            this.correctiveDelta -= delta;
        }
    }

    public double execute(double value) {
        return value + correctiveDelta;
    }
}

