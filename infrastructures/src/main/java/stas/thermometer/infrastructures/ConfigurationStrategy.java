package stas.thermometer.infrastructures;

import stas.thermometer.infrastructures.personalExceptions.FileNotFoundException;

import java.util.Map;

public interface ConfigurationStrategy {

    Map<String, Map<String, String>> executeConfigurationReaderStrategy(String pathArg) throws FileNotFoundException;
}
