package stas.thermometer.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Configuration {

    private final Map<String, String> format;
    private final List<AggregatorMain> aggregators = new ArrayList<>();

    private final Map<String, Map<String, String>> readedConfiguration;

    public Configuration(ConfigurationReader reader) {
        this.readedConfiguration = reader.getReadedConfiguration();
        this.format = readedConfiguration.get("format");

        for (ValueType type : ValueType.values()) {
            if (readedConfiguration.containsKey(type.getType())) {
                try {
                    List<Double> profil = getProfilList(readedConfiguration.get(type.getType()));

                    Class<? extends Probe> probeType = type.getProbeClass();
                    AggregatorMain aggregatorInstance = new AggregatorMain(
                            type.getType(),
                            probeType.getDeclaredConstructor(List.class).newInstance(profil),
                            type.getDelta()
                    );

                    this.aggregators.add(aggregatorInstance);

                } catch (Exception e) {
                    //ce cas de figure est litéralement impossible sauf si l'enum ValueType est mal construite
                    this.aggregators.add(null);
                }
            }
        }

    }

    public Map<String, String> getFormat() {
        return format;
    }



    public List<AggregatorMain> getAggregator() {
        return this.aggregators;
    }


    private List<Double> getProfilList(Map<String, String> profile) {
        return profile.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Tri des entrées par clé (l'ordre d'insertion est préservé)
                .map(entry -> Double.parseDouble(entry.getValue())) // Conversion des valeurs en Double
                .collect(Collectors.toList()); // Collecte des éléments dans une liste
    }

    public String getThermometerName() {
        return readedConfiguration.get("general").get("name");
    }
}
