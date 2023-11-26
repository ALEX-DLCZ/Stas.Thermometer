package stas.thermometer.infrastructures;

import stas.thermometer.domains.AggregatorMain;
import stas.thermometer.domains.ThermometerInterface;
import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @implNote cette classe est grandement inspirée de la classe Publisher du pattern Observer.
 * <p>
 * cohesive set : Supposition d'une erreur de pmd car si on suit l'exemple du pattern observer du site
 * https://refactoring.guru/design-patterns/observer/java/example, ma classe est presque en tout points identique a la leur.
 * De plus, meme en déplaçant le field name dans une autre classe, l'erreur persiste.
 * cependant, l'erreur peut etre du a la méthode getAggregatorsAccessor qui retourne une liste de AggregatorAccessor
 * et non de AggregatorMain mais dapres refactoring.guru, on peut avoir une méthode mainBusinessLogical dans le publisher
 */
public class Thermometer implements ThermometerInterface {

    private final String name;
    private final List<AggregatorMain> aggregatorMains;

    public Thermometer(String name) {
        this.name = name;
        this.aggregatorMains = new ArrayList<>();
    }

    @Override
    public String getThermometerName() {
        return this.name;
    }

    @Override
    public List<AggregatorAccessor> getAggregatorsAccessor() {
        return new ArrayList<>(this.aggregatorMains);
    }

    //------------DP OBSERVER--------------
    public void Subscribe(AggregatorMain aggregatorMain) {
        this.aggregatorMains.add(aggregatorMain);
    }

    //    public void Unsubscribe(AggregatorMain aggregatorMain) {
    //        this.aggregatorMains.remove(aggregatorMain);
    //    }
    @Override
    public void notifyUpdate() {
        for ( AggregatorMain aggregatorMain : this.aggregatorMains ) {
            aggregatorMain.updateAgregatedValues();
        }
    }
    //--------------------------

}
