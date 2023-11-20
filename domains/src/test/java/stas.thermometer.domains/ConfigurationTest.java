package stas.thermometer.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConfigurationTest {



    private final Map<String, Map<String, String>> configWhenGRC = new HashMap<>();


    @Mock
    ConfigurationReader mockReader = mock(ConfigurationReader.class);


    @BeforeEach
    public void setup() {

        Map<String, String> generalWhenGRC = new HashMap<>();
        Map<String, String> formatWhenGRC = new HashMap<>();
        Map<String, String> temperatureWhenGRC = new HashMap<>();
        Map<String, String> humidityWhenGRC = new HashMap<>();

        generalWhenGRC.put("name", "BHAAAaaaNAME");
        formatWhenGRC.put("temperature", "00.00°");
        formatWhenGRC.put("humidity", "0%");
        formatWhenGRC.put("datetime", "dd/MM/YYYY à HH :mm :ss");
        temperatureWhenGRC.put("j00", "18");
        temperatureWhenGRC.put("j01", "19");
        temperatureWhenGRC.put("j02", "20");
        temperatureWhenGRC.put("j03", "19");
        humidityWhenGRC.put("j00", "0.5");
        humidityWhenGRC.put("j01", "0.02");
        humidityWhenGRC.put("j02", "0.0064");
        humidityWhenGRC.put("j03", "0.209");

        configWhenGRC.put("general", generalWhenGRC);
        configWhenGRC.put("format", formatWhenGRC);
        configWhenGRC.put("temperature", temperatureWhenGRC);
        configWhenGRC.put("humidity", humidityWhenGRC);

        when(mockReader.getReadedConfiguration()).thenReturn(configWhenGRC);


    }


    @Test
    public void Should_Return_Thermometer_Object_when_ConfigurationConstructeur_is_Correct_and_creatThermometerCall(){

        Configuration configuration = new Configuration(mockReader);

        Thermometer thermometer = configuration.createThermometer();

        assertNotNull(thermometer);
        assertEquals("BHAAAaaaNAME", thermometer.getName());
    }

    @Test
    public void Should_Return_ProbesList_when_getProbesCall(){

        Configuration configuration = new Configuration(mockReader);

        List<Probe> probes = configuration.getProbes();

        assertEquals(2, probes.size());
        assertEquals(ProbeTemperature.class, probes.get(0).getClass());
        assertEquals(ProbeHumidity.class, probes.get(1).getClass());
    }

    @Test
    public void Should_Return_FormatMap_when_getFormatCall(){

        Configuration configuration = new Configuration(mockReader);

        Map<String, String> format = configuration.getFormat();

        assertEquals(3, format.size());
        assertEquals("00.00°", format.get("temperature"));
        assertEquals("0%", format.get("humidity"));
        assertEquals("dd/MM/YYYY à HH :mm :ss", format.get("datetime"));
    }



    @Test
    public void Should_Test_HappyPath_when_ConfigurationConstructeur_but_newInstanceFail(){

        ConfigurationReader mockReaderHappyPath = mock(ConfigurationReader.class);
        Map<String, Map<String, String>> configWithoutCorrectProfil = new HashMap<>();
        Map<String, String> generalWithoutCorrectProfil = new HashMap<>();
        Map<String, String> formatWithoutCorrectProfil = new HashMap<>();
        Map<String, String> humidityWithoutCorrectProfil = new HashMap<>();
        generalWithoutCorrectProfil.put("name", "BHAAAaaaNAME");
        formatWithoutCorrectProfil.put("temperature", "00.00°");
        formatWithoutCorrectProfil.put("humidity", "0%");
        formatWithoutCorrectProfil.put("datetime", "dd/MM/YYYY à HH :mm :ss");
        configWithoutCorrectProfil.put("general", generalWithoutCorrectProfil);
        configWithoutCorrectProfil.put("format", formatWithoutCorrectProfil);
        configWithoutCorrectProfil.put("humidity", humidityWithoutCorrectProfil);

        //ERREUR HAPPY PATH
        configWithoutCorrectProfil.put("temperature", null);

        when(mockReaderHappyPath.getReadedConfiguration()).thenReturn(configWithoutCorrectProfil);

        Configuration configuration = new Configuration(mockReaderHappyPath);

        List<Probe> probes = configuration.getProbes();

        assertEquals(1, probes.size());
        assertEquals(ProbeHumidity.class, probes.get(0).getClass());
    }


    @Test
    public void Should_Return_ProbesList_but_with_only_humidity_probe(){
        ConfigurationReader mockReaderHappyPath = mock(ConfigurationReader.class);
        Map<String, Map<String, String>> configWithoutCorrectProfil = new HashMap<>();
        Map<String, String> generalWithoutCorrectProfil = new HashMap<>();
        Map<String, String> formatWithoutCorrectProfil = new HashMap<>();
        Map<String, String> humidityWithoutCorrectProfil = new HashMap<>();
        generalWithoutCorrectProfil.put("name", "BHAAAaaaNAME");
        formatWithoutCorrectProfil.put("temperature", "00.00°");
        formatWithoutCorrectProfil.put("humidity", "0%");
        formatWithoutCorrectProfil.put("datetime", "dd/MM/YYYY à HH :mm :ss");
        configWithoutCorrectProfil.put("general", generalWithoutCorrectProfil);
        configWithoutCorrectProfil.put("format", formatWithoutCorrectProfil);
        configWithoutCorrectProfil.put("humidity", humidityWithoutCorrectProfil);

        humidityWithoutCorrectProfil.put("j00", "0.5");
        humidityWithoutCorrectProfil.put("j01", "0.02");
        humidityWithoutCorrectProfil.put("j02", "0.0064");
        humidityWithoutCorrectProfil.put("j03", "0.209");


        when(mockReaderHappyPath.getReadedConfiguration()).thenReturn(configWithoutCorrectProfil);
        Configuration configuration = new Configuration(mockReaderHappyPath);

        List<Probe> probes = configuration.getProbes();

        assertEquals(1, probes.size());
        assertEquals(ProbeHumidity.class, probes.get(0).getClass());


    }

}
