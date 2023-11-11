package stas.thermometer.infrastructures;

import stas.thermometer.infrastructures.personalExceptions.FileNotFoundException;

import java.util.HashMap;

public interface ConfigurationStrategy {

    HashMap<String, HashMap<String, String>> executeConfigurationReaderStrategy(String pathArg) throws FileNotFoundException;
}
