package stas.thermometer.infrastructures;

import stas.thermometer.domains.ConfigurationReader;
import stas.thermometer.infrastructures.personalExceptions.FileNotFoundException;
import stas.thermometer.infrastructures.personalExceptions.unknownArgumentException;

import java.util.HashMap;
import java.util.Map;

public class MainConfigurationReader implements ConfigurationReader {

    private final Map<String, Map<String, String>> configurationMap;
    public MainConfigurationReader(String pathArgs) throws unknownArgumentException, FileNotFoundException {

        String fileExtension;

        if (pathArgs.contains(".")) {
            fileExtension = pathArgs.substring(pathArgs.lastIndexOf(".") + 1);
        } else {
            throw new unknownArgumentException();
        }
        ConfigurationStrategy configurationStrategy;
        if (fileExtension.equals("ini")) {
            configurationStrategy = new IniConfigurationReader();
        } else {
            throw new unknownArgumentException();
        }

        configurationMap = configurationStrategy.executeConfigurationReaderStrategy(pathArgs);
    }

    @Override
    public Map<String, Map<String, String>> getReadedConfiguration() {
        return configurationMap;
    }
}
