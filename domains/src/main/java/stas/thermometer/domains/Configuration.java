package stas.thermometer.domains;

import stas.thermometer.domains.personal.exceptions.NameNotFoundException;
import stas.thermometer.domains.personal.exceptions.PropertyNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Configuration {

    private final Map<String, Map<String, String>> readedConfigurationMap;

    public Configuration(ConfigurationReader reader) throws PropertyNotFoundException, NameNotFoundException {

        this.readedConfigurationMap = reader.getReadedConfiguration();

        if (!readedConfigurationMap.containsKey("general") || !readedConfigurationMap.containsKey("format")) {
            throw new PropertyNotFoundException();
        }
        if (!readedConfigurationMap.get("general").containsKey("name")) {
            throw new NameNotFoundException();
        }
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
