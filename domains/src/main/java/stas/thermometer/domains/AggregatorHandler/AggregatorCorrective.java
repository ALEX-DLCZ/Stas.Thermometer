package stas.thermometer.domains.AggregatorHandler;

public class AggregatorCorrective{

    private double correctiveDelta = 0.0;

    public void adjustCorrectiveDelta(double correctiveDelta) {
        this.correctiveDelta += correctiveDelta;
    }

    public double execute(double value) {
        return value + correctiveDelta;
    }
}
