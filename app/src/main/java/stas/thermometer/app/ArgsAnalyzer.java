package stas.thermometer.app;

import stas.thermometer.infrastructures.ConfigurationStrategy;
import stas.thermometer.infrastructures.IniConfigurationReader;
import stas.thermometer.infrastructures.MainConfigurationReader;
import stas.thermometer.infrastructures.ThermometerRepository;
import stas.thermometer.infrastructures.personalExceptions.FileNotFoundException;
import stas.thermometer.infrastructures.personalExceptions.unknownArgumentException;

import java.util.Objects;

public class ArgsAnalyzer {

    MainConfigurationReader configurationStrategy;

    public ArgsAnalyzer(String[] args) {

        try {
            if (Objects.equals(args[0], "--config-file")) {

                configurationStrategy = new MainConfigurationReader(args[1]);

            } else {
                throw new Exception();
            }


        } catch (unknownArgumentException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            //DEFAULT CONFIGURATION
            //MainConfigurationReader  configurationStrategy = new MainConfigurationReader("dummyConfig.ini");


        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("stas: missing configuration file argument");
            //configuration.setConfigurationReaderStrategy(new IniConfigurationReader());
            //configuration.executeConfigurationReaderStrategy("dummyConfig.ini");

        } catch (Exception e) {
            System.out.println("stas: Unknown argument");
        }
    }

    public MainConfigurationReader getConfiguration() {
        return configurationStrategy;
    }
}
