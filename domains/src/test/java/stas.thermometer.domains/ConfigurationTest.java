package stas.thermometer.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import stas.thermometer.domains.personal.exceptions.NameNotFoundException;
import stas.thermometer.domains.personal.exceptions.PropertyNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void Should_Return_ThermometerName_when_ConfigurationReader_getReadedConfiguration_Return_Correct_Map() {

        Configuration configuration = null;
        try {
            configuration = new Configuration(mockReader);
        } catch ( PropertyNotFoundException | NameNotFoundException e ) {
            fail();
        }

        String thermometerName = configuration.getThermometerName();

        assertEquals("BHAAAaaaNAME", thermometerName);

    }

    @Test
    public void Should_Return_FormatMap_when_ConfigurationReader_getReadedConfiguration_Return_Correct_Map() {

        Configuration configuration = null;
        try {
            configuration = new Configuration(mockReader);
        } catch ( PropertyNotFoundException | NameNotFoundException e ) {
            fail();
        }

        Map<String, String> format = configuration.getReadedConfiguration().get("format");

        assertEquals(3, format.size());
        assertEquals("00.00°", format.get("temperature"));
        assertEquals("0%", format.get("humidity"));
        assertEquals("dd/MM/YYYY à HH :mm :ss", format.get("datetime"));
    }

    @Test
    public void Should_Return_TemperatureProfil_when_ConfigurationReader_getReadedConfiguration_Return_Correct_Map() {

        Configuration configuration = null;
        try {
            configuration = new Configuration(mockReader);
        } catch ( PropertyNotFoundException | NameNotFoundException e ) {
            fail();
        }

        Map<String, String> temperatureProfil = configuration.getReadedConfiguration().get("temperature");

        assertEquals(4, temperatureProfil.size());
        assertEquals("18", temperatureProfil.get("j00"));
        assertEquals("19", temperatureProfil.get("j01"));
        assertEquals("20", temperatureProfil.get("j02"));
        assertEquals("19", temperatureProfil.get("j03"));
    }

    @Test
    public void Should_Return_Sorted_ListOfDouble_when_getProfilListCall() {

        Configuration configuration = null;
        try {
            configuration = new Configuration(mockReader);
        } catch ( PropertyNotFoundException | NameNotFoundException e ) {
            fail();
        }

        List<Double> profilList = configuration.getProfilList(configuration.getReadedConfiguration().get("temperature"));

        assertEquals(4, profilList.size());
        assertEquals(18.0, profilList.get(0));
        assertEquals(19.0, profilList.get(1));
        assertEquals(20.0, profilList.get(2));
        assertEquals(19.0, profilList.get(3));
    }

    @Test
    public void Should_Return_Sorted_ListOfDouble_when_getProfilListCall_with_humidityProfil() {

        Configuration configuration = null;
        try {
            configuration = new Configuration(mockReader);
        } catch ( PropertyNotFoundException | NameNotFoundException e ) {
            fail();
        }

        List<Double> profilList = configuration.getProfilList(configuration.getReadedConfiguration().get("humidity"));

        assertEquals(4, profilList.size());
        assertEquals(0.5, profilList.get(0));
        assertEquals(0.02, profilList.get(1));
        assertEquals(0.0064, profilList.get(2));
        assertEquals(0.209, profilList.get(3));
    }

    @Test
    public void Should_Throw_PropertyNotFoundException_when_ConfigurationReader_getReadedConfiguration_Return_Map_Without_GeneralKey() {

        ConfigurationReader mockReaderWithoutGeneralKey = mock(ConfigurationReader.class);
        Map<String, Map<String, String>> configWithoutGeneralKey = new HashMap<>();
        Map<String, String> formatWithoutGeneralKey = new HashMap<>();
        Map<String, String> temperatureWithoutGeneralKey = new HashMap<>();
        Map<String, String> humidityWithoutGeneralKey = new HashMap<>();

        formatWithoutGeneralKey.put("temperature", "00.00°");
        formatWithoutGeneralKey.put("humidity", "0%");
        formatWithoutGeneralKey.put("datetime", "dd/MM/YYYY à HH :mm :ss");
        temperatureWithoutGeneralKey.put("j00", "18");
        temperatureWithoutGeneralKey.put("j01", "19");
        temperatureWithoutGeneralKey.put("j02", "20");
        temperatureWithoutGeneralKey.put("j03", "19");
        humidityWithoutGeneralKey.put("j00", "0.5");
        humidityWithoutGeneralKey.put("j01", "0.02");
        humidityWithoutGeneralKey.put("j02", "0.0064");
        humidityWithoutGeneralKey.put("j03", "0.209");

        configWithoutGeneralKey.put("format", formatWithoutGeneralKey);
        configWithoutGeneralKey.put("temperature", temperatureWithoutGeneralKey);
        configWithoutGeneralKey.put("humidity", humidityWithoutGeneralKey);

        when(mockReaderWithoutGeneralKey.getReadedConfiguration()).thenReturn(configWithoutGeneralKey);

        Configuration configuration = null;
        try {
            configuration = new Configuration(mockReaderWithoutGeneralKey);
        } catch ( PropertyNotFoundException | NameNotFoundException e ) {
            assertTrue(e instanceof PropertyNotFoundException);
        }
    }

    @Test
    public void Should_Throw_PropertyNotFoundException_when_ConfigurationReader_getReadedConfiguration_Return_Map_Without_FormatKey() {

        ConfigurationReader mockReaderWithoutGeneralKey = mock(ConfigurationReader.class);
        Map<String, Map<String, String>> configWithoutGeneralKey = new HashMap<>();
        Map<String, String> generalWithoutGeneralKey = new HashMap<>();
        Map<String, String> formatWithoutGeneralKey = new HashMap<>();
        Map<String, String> temperatureWithoutGeneralKey = new HashMap<>();
        Map<String, String> humidityWithoutGeneralKey = new HashMap<>();

        generalWithoutGeneralKey.put("name", "BHAAAaaaNAME");
        temperatureWithoutGeneralKey.put("j00", "18");
        temperatureWithoutGeneralKey.put("j01", "19");
        temperatureWithoutGeneralKey.put("j02", "20");
        temperatureWithoutGeneralKey.put("j03", "19");
        humidityWithoutGeneralKey.put("j00", "0.5");
        humidityWithoutGeneralKey.put("j01", "0.02");
        humidityWithoutGeneralKey.put("j02", "0.0064");
        humidityWithoutGeneralKey.put("j03", "0.209");


        configWithoutGeneralKey.put("general", generalWithoutGeneralKey);
        configWithoutGeneralKey.put("temperature", temperatureWithoutGeneralKey);
        configWithoutGeneralKey.put("humidity", humidityWithoutGeneralKey);

        when(mockReaderWithoutGeneralKey.getReadedConfiguration()).thenReturn(configWithoutGeneralKey);

        Configuration configuration = null;
        try {
            configuration = new Configuration(mockReaderWithoutGeneralKey);
        } catch ( PropertyNotFoundException | NameNotFoundException e ) {
            assertTrue(e instanceof PropertyNotFoundException);
        }
    }

    @Test
    public void Should_Throw_NameNotFoundException_when_ConfigurationReader_getReadedConfiguration_Return_Map_Without_NameKey() {

        ConfigurationReader mockReaderWithoutNameKey = mock(ConfigurationReader.class);
        Map<String, Map<String, String>> configWithoutNameKey = new HashMap<>();
        Map<String, String> generalWithoutNameKey = new HashMap<>();
        Map<String, String> formatWithoutNameKey = new HashMap<>();
        Map<String, String> temperatureWithoutNameKey = new HashMap<>();
        Map<String, String> humidityWithoutNameKey = new HashMap<>();

        formatWithoutNameKey.put("temperature", "00.00°");
        formatWithoutNameKey.put("humidity", "0%");
        formatWithoutNameKey.put("datetime", "dd/MM/YYYY à HH :mm :ss");
        temperatureWithoutNameKey.put("j00", "18");
        temperatureWithoutNameKey.put("j01", "19");
        temperatureWithoutNameKey.put("j02", "20");
        temperatureWithoutNameKey.put("j03", "19");
        humidityWithoutNameKey.put("j00", "0.5");
        humidityWithoutNameKey.put("j01", "0.02");
        humidityWithoutNameKey.put("j02", "0.0064");
        humidityWithoutNameKey.put("j03", "0.209");

        configWithoutNameKey.put("general", generalWithoutNameKey);
        configWithoutNameKey.put("format", formatWithoutNameKey);
        configWithoutNameKey.put("temperature", temperatureWithoutNameKey);
        configWithoutNameKey.put("humidity", humidityWithoutNameKey);

        when(mockReaderWithoutNameKey.getReadedConfiguration()).thenReturn(configWithoutNameKey);

        Configuration configuration = null;
        try {
            configuration = new Configuration(mockReaderWithoutNameKey);
        } catch ( PropertyNotFoundException | NameNotFoundException e ) {
            assertTrue(e instanceof NameNotFoundException);
        }

    }


    //
    //    private final Map<String, Map<String, String>> configWhenGRC = new HashMap<>();
    //
    //
    //    @Mock
    //    ConfigurationReader mockReader = mock(ConfigurationReader.class);
    //
    //
    //    @BeforeEach
    //    public void setup() {
    //
    //        Map<String, String> generalWhenGRC = new HashMap<>();
    //        Map<String, String> formatWhenGRC = new HashMap<>();
    //        Map<String, String> temperatureWhenGRC = new HashMap<>();
    //        Map<String, String> humidityWhenGRC = new HashMap<>();
    //
    //        generalWhenGRC.put("name", "BHAAAaaaNAME");
    //        formatWhenGRC.put("temperature", "00.00°");
    //        formatWhenGRC.put("humidity", "0%");
    //        formatWhenGRC.put("datetime", "dd/MM/YYYY à HH :mm :ss");
    //        temperatureWhenGRC.put("j00", "18");
    //        temperatureWhenGRC.put("j01", "19");
    //        temperatureWhenGRC.put("j02", "20");
    //        temperatureWhenGRC.put("j03", "19");
    //        humidityWhenGRC.put("j00", "0.5");
    //        humidityWhenGRC.put("j01", "0.02");
    //        humidityWhenGRC.put("j02", "0.0064");
    //        humidityWhenGRC.put("j03", "0.209");
    //
    //        configWhenGRC.put("general", generalWhenGRC);
    //        configWhenGRC.put("format", formatWhenGRC);
    //        configWhenGRC.put("temperature", temperatureWhenGRC);
    //        configWhenGRC.put("humidity", humidityWhenGRC);
    //
    //        when(mockReader.getReadedConfiguration()).thenReturn(configWhenGRC);
    //
    //
    //    }
    //
    //
    //    @Test
    //    public void Should_Return_AggregatorsList_when_getProbesCall(){
    //
    //        Configuration configuration = new Configuration(mockReader);
    //
    //        List<AggregatorMain> aggregators = configuration.getAggregator();
    //
    //        assertEquals(2, aggregators.size());
    //        assertEquals(AggregatorMain.class, aggregators.get(0).getClass());
    //        assertEquals(AggregatorMain.class, aggregators.get(1).getClass());
    //        assertEquals("temperature", aggregators.get(0).getName());
    //        assertEquals("humidity", aggregators.get(1).getName());
    //
    //    }
    //
    //    @Test
    //    public void Should_Return_FormatMap_when_getFormatCall(){
    //
    //        Configuration configuration = new Configuration(mockReader);
    //
    //        Map<String, String> format = configuration.getFormat();
    //
    //        assertEquals(3, format.size());
    //        assertEquals("00.00°", format.get("temperature"));
    //        assertEquals("0%", format.get("humidity"));
    //        assertEquals("dd/MM/YYYY à HH :mm :ss", format.get("datetime"));
    //    }
    //
    //
    //
    //    @Test
    //    public void Should_Test_HappyPath_when_ConfigurationConstructeur_but_newInstanceFail(){
    //
    //        ConfigurationReader mockReaderHappyPath = mock(ConfigurationReader.class);
    //        Map<String, Map<String, String>> configWithoutCorrectProfil = new HashMap<>();
    //        Map<String, String> generalWithoutCorrectProfil = new HashMap<>();
    //        Map<String, String> formatWithoutCorrectProfil = new HashMap<>();
    //        Map<String, String> humidityWithoutCorrectProfil = new HashMap<>();
    //        generalWithoutCorrectProfil.put("name", "BHAAAaaaNAME");
    //        formatWithoutCorrectProfil.put("temperature", "00.00°");
    //        formatWithoutCorrectProfil.put("humidity", "0%");
    //        formatWithoutCorrectProfil.put("datetime", "dd/MM/YYYY à HH :mm :ss");
    //        configWithoutCorrectProfil.put("general", generalWithoutCorrectProfil);
    //        configWithoutCorrectProfil.put("format", formatWithoutCorrectProfil);
    //        configWithoutCorrectProfil.put("humidity", humidityWithoutCorrectProfil);
    //
    //        //ERREUR HAPPY PATH
    //        configWithoutCorrectProfil.put("temperature", null);
    //
    //        when(mockReaderHappyPath.getReadedConfiguration()).thenReturn(configWithoutCorrectProfil);
    //
    //        Configuration configuration = new Configuration(mockReaderHappyPath);
    //
    //        List<AggregatorMain> aggregators = configuration.getAggregator();
    //
    //        assertEquals(2, aggregators.size());
    //        assertNull(aggregators.get(0));
    //        assertEquals( AggregatorMain.class, aggregators.get(1).getClass());
    //    }
    //
    //
    //    @Test
    //    public void Should_Return_aggregatorsList_but_with_only_humidity_probe(){
    //        ConfigurationReader mockReaderHappyPath = mock(ConfigurationReader.class);
    //        Map<String, Map<String, String>> configWithoutCorrectProfil = new HashMap<>();
    //        Map<String, String> generalWithoutCorrectProfil = new HashMap<>();
    //        Map<String, String> formatWithoutCorrectProfil = new HashMap<>();
    //        Map<String, String> humidityWithoutCorrectProfil = new HashMap<>();
    //        generalWithoutCorrectProfil.put("name", "BHAAAaaaNAME");
    //        formatWithoutCorrectProfil.put("temperature", "00.00°");
    //        formatWithoutCorrectProfil.put("humidity", "0%");
    //        formatWithoutCorrectProfil.put("datetime", "dd/MM/YYYY à HH :mm :ss");
    //        configWithoutCorrectProfil.put("general", generalWithoutCorrectProfil);
    //        configWithoutCorrectProfil.put("format", formatWithoutCorrectProfil);
    //        configWithoutCorrectProfil.put("humidity", humidityWithoutCorrectProfil);
    //
    //        humidityWithoutCorrectProfil.put("j00", "0.5");
    //        humidityWithoutCorrectProfil.put("j01", "0.02");
    //        humidityWithoutCorrectProfil.put("j02", "0.0064");
    //        humidityWithoutCorrectProfil.put("j03", "0.209");
    //
    //
    //        when(mockReaderHappyPath.getReadedConfiguration()).thenReturn(configWithoutCorrectProfil);
    //        Configuration configuration = new Configuration(mockReaderHappyPath);
    //
    //        List<AggregatorMain> aggregators = configuration.getAggregator();
    //
    //        assertEquals(1, aggregators.size());
    //        assertEquals("humidity", aggregators.get(0).getName());
    //
    //
    //    }

}
