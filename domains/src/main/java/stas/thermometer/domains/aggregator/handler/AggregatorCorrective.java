package stas.thermometer.domains.aggregator.handler;


/**
 * @implNote cette classe permet de gérer la correction de l'aggrégateur aussi appelé delta. elle permet de déterminer le delta.
 *
 * Cohesive set: la class est relativement courte et ne fait qu'une seule chose. impossibilité de la découper en sous-classe.
 * adjustCorrectiveDelta est appelé quand on souhaite ajuster le delta.
 * execute est appelé quand on souhaite appliquer le delta.
 * en outre, la classe ne peut pas etre plus courte qu'elle ne l'est deja.
 */
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

