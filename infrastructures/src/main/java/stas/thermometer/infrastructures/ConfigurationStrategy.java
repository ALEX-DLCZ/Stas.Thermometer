package stas.thermometer.infrastructures;

import stas.thermometer.infrastructures.personal.exceptions.FileNotFoundException;

import java.util.Map;

public interface ConfigurationStrategy {

    /**
     * unique méthode de l'interface ConfigurationStrategy inspirée du design pattern Strategy
     *
     * @param pathArg
     * @return
     * @throws FileNotFoundException
     */
    Map<String, Map<String, String>> executeConfigurationReaderStrategy(String pathArg) throws FileNotFoundException;
}
