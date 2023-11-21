package stas.thermometer.infrastructures;

import stas.thermometer.infrastructures.personal.exceptions.FileNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class IniConfigurationReader implements ConfigurationStrategy {

    private final Map<String, Map<String, String>> sectionMaps = new HashMap<>();
    private Map<String, String> currentSection;


    /**
     * @param pathArg chemin vers le fichier de configuration
     * @return Map<String, Map < String, String>> sectionMaps  qui contient toute les sections et leurs valeurs
     * @implNote executeConfigurationReaderStrategy va lire toute les ligne d'un fichier une par une et executer la commande ReadLine,
     * si la ligne est un commentaire ou vide, elle va passer a la ligne suivante, si la ligne est une nouvelle section elle va creer une nouvelle section,
     * si la ligne est une paire de valeur elle va ajouter la valeur a la section courante
     */
    @Override
    public Map<String, Map<String, String>> executeConfigurationReaderStrategy(String pathArg) throws FileNotFoundException {

        try {
            //va lire toute les ligne d'un fichier une par une et executer la commande ReadLine
            for (String line : Files.readAllLines(Paths.get(pathArg))) {
                readLine(line);
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        return sectionMaps;
    }

    //---------- private methods ----------
    private void readLine(String line) {

        if (isCommentOrEmpty(line)) {
            return;
        } else if (isNewSection(line)) {
            handleNewSection(line);
        } else if (IsValuePair(line)) {
            processKeyValuePair(line);
        }
    }

    private boolean isCommentOrEmpty(String line) {
        return line.isEmpty() || line.startsWith(";") || line.startsWith("#");
    }

    private boolean isNewSection(String line) {
        return line.startsWith("[") && line.endsWith("]");
    }

    private void handleNewSection(String line) {
        String sectionName = line.substring(1, line.length() - 1);
        currentSection = new HashMap<>();
        sectionMaps.put(sectionName, currentSection);
    }

    private void processKeyValuePair(String line) {
        String[] parts = line.split("=", 2);
        String key = parts[0].trim();
        String value = parts[1].trim();
        currentSection.put(key, value);
    }

    private boolean IsValuePair(String line) {
        return line.contains("=");
    }


}
