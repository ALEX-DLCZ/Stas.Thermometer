package stas.thermometer.infrastructures.database;

import java.time.LocalDateTime;

/**
 * @implNote cette classe est un simple dto et pourrait avoir des setters dans le futur.
 * représente la table Mesure sans le champ id pour éviter les base de données sans clé primaire ID
 * <p>
 * cohesive set: possible erreur de pmd car ... c'est un simple dto comme Alert
 * <p>
 * Avoid long parameter lists: pas d'autre possibilité que de passer 5 paramètres étant donné que la table mesure en a 5 également
 * <p>
 * suspected to be a Data Class: ... todo
 */

public record Mesure(String thermometerName, LocalDateTime datetime, String type, String format, double value) {

}
