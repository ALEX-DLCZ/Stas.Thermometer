package stas.thermometer.infrastructures.database;


/**
 * @implNote cette classe est un simple dto et pourrait avoir des setters dans le futur,
 * représente la table Alert sans le champ id pour éviter les base de données sans clé primaire ID
 * <p>
 * cohesive set: possible erreur de pmd car ... c'est un simple dto comme Mesure
 */
public record Alert(double expectedValue, int idMesure) {


    //--------------------------

}
