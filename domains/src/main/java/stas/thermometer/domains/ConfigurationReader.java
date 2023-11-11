package stas.thermometer.domains;

import java.util.HashMap;

public interface ConfigurationReader {
    HashMap<String, HashMap<String, String>> getReadedConfiguration();

}
