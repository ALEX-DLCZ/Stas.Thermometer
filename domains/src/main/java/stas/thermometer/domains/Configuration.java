package stas.thermometer.domains;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Configuration {

    private final Map<String, String> format;
    private final List<Probe> probes = new ArrayList<>();

    private final Map<String, Map<String, String>> readedConfiguration;

    public Configuration(ConfigurationReader reader) {
        this.readedConfiguration = reader.getReadedConfiguration();
        this.format = readedConfiguration.get("format");

        for (ValueType type : ValueType.values()) {
            if (readedConfiguration.containsKey(type.getType())) try {
                List<Double> profil = getProfilList(readedConfiguration.get(type.getType()));

                Class<? extends Probe> probeType = type.probeClass();

                Probe probeInstance = probeType.getDeclaredConstructor(List.class).newInstance(profil);

                this.probes.add(probeInstance);

            } catch (Exception e) {
                //ce cas de figure est litéralement impossible sauf si l'enum ValueType est mal construite
            }
        }

    }

    public Map<String, String> getFormat() {
        return format;
    }


    public Thermometer createThermometer() {
        return new Thermometer(readedConfiguration.get("general").get("name"));
    }
    public List<Probe> getProbes() {
        return probes;
    }



    private List<Double> getProfilList(Map<String, String> profile) {
        return profile.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Tri des entrées par clé (l'ordre d'insertion est préservé)
                .map(entry -> Double.parseDouble(entry.getValue())) // Conversion des valeurs en Double
                .collect(Collectors.toList()); // Collecte des éléments dans une liste
    }
}
