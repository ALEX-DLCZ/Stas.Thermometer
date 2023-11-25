package stas.thermometer.app;

import stas.thermometer.app.personalExceptions.fatalException;
import stas.thermometer.domains.AggregatorMain;
import stas.thermometer.domains.Configuration;
import stas.thermometer.domains.Probe;
import stas.thermometer.domains.ValueType;
import stas.thermometer.infrastructures.MainConfigurationReader;
import stas.thermometer.infrastructures.Thermometer;
import stas.thermometer.infrastructures.personal.exceptions.FileNotFoundException;
import stas.thermometer.infrastructures.personal.exceptions.unknownArgumentException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @implNote cette class va s'occuper de la gestion des arguments pour instancier/créer les objets nécessaires au programme
 * en fonction des arguments
 */
public class ArgsExecutor {



    private final List<AggregatorMain> aggregators = new ArrayList<>();
    private final Thermometer thermometer;
    private final Map<String, String> formatmap;
    private final String connectionString;



    public ArgsExecutor(String[] args) throws fatalException {

        Configuration configuration = new Configuration(argsAnalyzer(args));
        Map<String, Map<String, String>> readedConfigurationMap = configuration.getReadedConfiguration();

        for (ValueType type : ValueType.values()) {
            if (readedConfigurationMap.containsKey(type.getType())) {
                try {
                    List<Double> profil = configuration.getProfilList(readedConfigurationMap.get(type.getType()));

                    Class<? extends Probe> probeType = type.getProbeClass();
                    AggregatorMain aggregatorInstance = new AggregatorMain(
                            type.getType(),
                            probeType.getDeclaredConstructor(List.class).newInstance(profil),
                            type.getDelta()
                    );

                    this.aggregators.add(aggregatorInstance);

                } catch (InvocationTargetException |NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                    throw new fatalException(e.getMessage());
                }
            }
        }

        this.thermometer = new Thermometer(configuration.getThermometerName());
        this.formatmap = readedConfigurationMap.get("format");

        this.connectionString = "jdbc:mysql://" +
                readedConfigurationMap.get("BD").get("IpServer") + ":" +
                readedConfigurationMap.get("BD").get("PortServer") + "/" +
                readedConfigurationMap.get("BD").get("User") + "?" +
                "user=" + readedConfigurationMap.get("BD").get("User") + "&" +
                "password=" + readedConfigurationMap.get("BD").get("Pws");
    }

    public Thermometer getThermometer() {
        return this.thermometer;
    }
    public List<AggregatorMain> getAggregators() {
        return this.aggregators;
    }
    public Map<String, String> getFormatmap() {
        return this.formatmap;
    }
    public String getConnectionString() {
        return this.connectionString;
    }
    private MainConfigurationReader argsAnalyzer(String[] args) throws fatalException {

        MainConfigurationReader configurationStrategy;
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
        return configurationStrategy;

    }

}
