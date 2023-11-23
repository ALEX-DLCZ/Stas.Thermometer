package stas.thermometer.app;

import stas.thermometer.app.personalExceptions.fatalException;
import stas.thermometer.infrastructures.MainConfigurationReader;
import stas.thermometer.infrastructures.personal.exceptions.FileNotFoundException;
import stas.thermometer.infrastructures.personal.exceptions.unknownArgumentException;

import java.util.Objects;

public class ArgsAnalyzer {

    MainConfigurationReader configurationStrategy;


    public ArgsAnalyzer(String[] args) throws fatalException {

        try {
            if (Objects.equals(args[0], "--config-file")) {

                configurationStrategy = new MainConfigurationReader(args[1]);

            } else {
                throw new Exception();
            }


        } catch (unknownArgumentException | FileNotFoundException e) {
            throw new fatalException(e.getMessage());

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new fatalException("missing configuration file argument");


        } catch (Exception e) {
            throw new fatalException("Unknown argument");
        }
    }

    public MainConfigurationReader getConfiguration() {
        return configurationStrategy;
    }
}
