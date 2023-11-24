package stas.thermometer.domains;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Configuration {

    private final Map<String, Map<String, String>> readedConfigurationMap;

    public Configuration(ConfigurationReader reader) {
        this.readedConfigurationMap = reader.getReadedConfiguration();
    }

    public Map<String, Map<String, String>> getReadedConfiguration() {
        return readedConfigurationMap;
    }

    public String getThermometerName() {
        return readedConfigurationMap.get("general").get("name");
    }

    public List<Double> getProfilList(Map<String, String> profile) {
        return profile.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Tri des entrées par clé (l'ordre d'insertion est préservé)
                .map(entry -> Double.parseDouble(entry.getValue())) // Conversion des valeurs en Double
                .collect(Collectors.toList()); // Collecte des éléments dans une liste
    }

}
