package stas.thermometer.domains;

@FunctionalInterface
public interface AggregatorSubscriber {

    void updateAggregatorNotification(String aggregatorName);
}
