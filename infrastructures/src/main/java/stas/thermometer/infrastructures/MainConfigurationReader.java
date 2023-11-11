package stas.thermometer.infrastructures;

import stas.thermometer.domains.ConfigurationReader;
import stas.thermometer.infrastructures.personalExceptions.FileNotFoundException;
import stas.thermometer.infrastructures.personalExceptions.unknownArgumentException;

import java.util.HashMap;

public class MainConfigurationReader implements ConfigurationReader {

    private final HashMap<String, HashMap<String, String>> configurationMap;
    public MainConfigurationReader(String pathArgs) throws unknownArgumentException, FileNotFoundException {

        String fileExtension;

        try {
            fileExtension = pathArgs.substring(pathArgs.lastIndexOf(".") + 1);
        } catch (Exception e) {
            fileExtension = "ERROR";
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
    public HashMap<String, HashMap<String, String>> getReadedConfiguration() {
        return configurationMap;
    }
}
