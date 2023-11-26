package stas.thermometer.infrastructures;

import stas.thermometer.infrastructures.personal.exceptions.FileNotFoundException;

import java.util.Map;

public interface ConfigurationStrategy {

    /**
     * unique méthode de l'interface ConfigurationStrategy inspirée du design pattern Strategy
     *
     * @param pathArg chemin du fichier de configuration
     * @return Map<String, Map<String, String>> qui contient les données du fichier de configuration
     * @throws FileNotFoundException si le fichier n'est pas trouvé
     */
    Map<String, Map<String, String>> executeConfigurationReaderStrategy(String pathArg) throws FileNotFoundException;
}
