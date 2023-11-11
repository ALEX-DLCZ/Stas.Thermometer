package stas.thermometer.infrastructures;

import stas.thermometer.infrastructures.personalExceptions.FileNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class IniConfigurationReader implements ConfigurationStrategy{

    private HashMap<String, HashMap<String, String>> sectionMaps;
    private HashMap<String, String> currentSection;

    public IniConfigurationReader() {
        this.sectionMaps = new HashMap<>();
    }

    @Override
    public HashMap<String, HashMap<String, String>> executeConfigurationReaderStrategy(String pathArg) throws FileNotFoundException {

        try{
            //va lire toute les ligne d'un fichier une par une et executer la commande ReadLine
            for (String line : Files.readAllLines(Paths.get(pathArg))) {
                readLine(line);
            }
        } catch (IOException e) {
            throw new FileNotFoundException();

        }

        return sectionMaps;


    }


    private void readLine(String line) {

        if ( isCommentOrEmpty(line) )
        {
            return;
        }
        else if ( isNewSection(line) )
        {
            handleNewSection(line);
        }
        else if ( IsValuePair(line) )
        {
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
        if (currentSection != null) {
            String[] parts = line.split("=", 2);
            String key = parts[0].trim();
            String value = parts[1].trim();
            currentSection.put(key, value);
        }
    }
    private boolean IsValuePair(String line) {
        return line.contains("=");
    }


}
