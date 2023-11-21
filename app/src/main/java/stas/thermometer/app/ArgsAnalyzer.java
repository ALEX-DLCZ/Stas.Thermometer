package stas.thermometer.app;

import stas.thermometer.app.personalExceptions.fatalException;
import stas.thermometer.infrastructures.MainConfigurationReader;
import stas.thermometer.infrastructures.personal.exceptions.FileNotFoundException;
import stas.thermometer.infrastructures.personal.exceptions.unknownArgumentException;

import java.util.Objects;
import java.util.logging.Logger;

public class ArgsAnalyzer {

    MainConfigurationReader configurationStrategy;

    private final Logger logger = Logger.getLogger(ArgsAnalyzer.class.getName());

    public ArgsAnalyzer(String[] args) throws fatalException {

        try {
            if (Objects.equals(args[0], "--config-file")) {

                configurationStrategy = new MainConfigurationReader(args[1]);

            } else {
                throw new Exception();
            }


        } catch (unknownArgumentException | FileNotFoundException e) {
            //System.out.println(e.getMessage());
            //DEFAULT CONFIGURATION
            //MainConfigurationReader  configurationStrategy = new MainConfigurationReader("dummyConfig.ini");
            //logger.info(e.getMessage());
            throw new fatalException(e.getMessage());

        } catch (ArrayIndexOutOfBoundsException e) {
            //System.out.println("stas: missing configuration file argument");
            //configuration.setConfigurationReaderStrategy(new IniConfigurationReader());
            //configuration.executeConfigurationReaderStrategy("dummyConfig.ini");
            //logger.info("stas: missing configuration file argument");
            throw new fatalException("missing configuration file argument");


        } catch (Exception e) {
            //System.out.println("stas: Unknown argument");
            //logger.info("stas: Unknown argument");
            throw new fatalException("Unknown argument");
        }
    }

    public MainConfigurationReader getConfiguration() {
        return configurationStrategy;
    }
}
