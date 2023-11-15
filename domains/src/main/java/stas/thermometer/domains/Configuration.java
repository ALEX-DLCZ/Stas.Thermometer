package stas.thermometer.domains;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Configuration {

    private final Thermometer thermometer;
    private final Map<String, String> format;
    private final List<Probe> aggregators ;

    public Configuration(ConfigurationReader reader) {
        this.aggregators = new ArrayList<>();
        Map<String, Map<String, String>> readedConfiguration = reader.getReadedConfiguration();
        this.format = readedConfiguration.get("format");
        this.thermometer = new Thermometer();

        for (ValueType type : ValueType.values()) {
            if (readedConfiguration.containsKey(type.getType())) {

                // TODO: 15/11/2023  get profil from configuration
                //List<Double> profil = List.of(1.0, 2.0, 3.0);
                List<Double> profil = getProfilList(readedConfiguration.get(type.getType()));



                try {
                    Class<? extends Probe> probeType = type.probeClass();

                    Probe probeInstance = probeType.getDeclaredConstructor(List.class).newInstance(profil);

                    this.aggregators.add(probeInstance);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
    public Thermometer getThermometer() {
        return this.thermometer;
    }

    public Map<String, String> getFormat() {
        return format;
    }


    private List<Double> getProfilList(Map<String, String> profile) {
        return profile.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Tri des entrées par clé (l'ordre d'insertion est préservé)
                .map(entry -> Double.parseDouble(entry.getValue())) // Conversion des valeurs en Double
                .collect(Collectors.toList()); // Collecte des éléments dans une liste

    }

}
