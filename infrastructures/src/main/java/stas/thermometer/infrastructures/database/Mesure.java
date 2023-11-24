package stas.thermometer.infrastructures.database;

import java.time.LocalDateTime;



/**
 * @implNote cette classe est un simple dto et pourrait avoir des setters dans le futur.
 * représente la table Mesure sans le champ id pour éviter les base de données sans clé primaire ID
 *
 * cohesive set: possible erreur de pmd car ... c'est un simple dto comme Alert
 *
 * Avoid long parameter lists: pas d'autre possibilité que de passer 5 paramètres étant donné que la table mesure en a 5 également
 *
 * suspected to be a Data Class: ... todo
 */

public class Mesure {

    private final String thermometerName;
    private final LocalDateTime datetime;
    private final String type;
    private final String format;
    private final double value;


    public Mesure(String thermometerName, LocalDateTime datetime, String type, String format, double value) {
        this.thermometerName = thermometerName;
        this.datetime = datetime;
        this.type = type;
        this.format = format;
        this.value = value;
    }

    //--------------------------

    public String getThermometerName() {
        return thermometerName;
    }
    public LocalDateTime getDatetime() {
        return datetime;
    }
    public String getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }
    public double getValue() {
        return value;
    }

}
