package stas.thermometer.domains;

import java.util.HashMap;
import java.util.Map;

public interface ConfigurationReader {
    Map<String, Map<String, String>> getReadedConfiguration();

}
