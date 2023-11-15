package stas.thermometer.infrastructures;

import org.junit.jupiter.api.Test;
import stas.thermometer.infrastructures.IniConfigurationReader;
import stas.thermometer.infrastructures.personalExceptions.FileNotFoundException;
import stas.thermometer.infrastructures.personalExceptions.unknownArgumentException;

import java.util.Map;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MainConfigurationReaderTest {

    /*

configBASIC.ini :
; Last modified 15 July 2010 by Juan Dona
[general]
name=Living Room 1
[format]
temperature=00.00°
humidity=0%
datetime=dd/MM/YYYY a HH :mm :ss
[temperature]
j00=18
j01=19
j02=20
j03=19
[humidity]
j00=0.5
j01=0.5
j02=0.0
j03=0.55
j04=0.01
j05=0.7
[BD]
IpServer= 192.168.132.200
PortServer= 13306
User= q210007
Pws= 0007


     */
    //Les tests unitaires suivent une convention de nommage telle que Should…, It…, ou Given...When...Then…
    //exemple : Should_Provide_His_View_With_Thermometer_Name ou Should_ReadIniFile_Without_Profiles ou autre


    @Test
    public void Should_Return_The_Same_Configuration_Than_In_The_File() {
        //Given
        String path = "src/test/resources/configBASIC.ini";
        MainConfigurationReader mainConfigurationReader = null;
        try {
            mainConfigurationReader = new MainConfigurationReader(path);
        } catch (FileNotFoundException | unknownArgumentException e) {
            e.printStackTrace();
            fail();
        }

        //When
        Map<String, Map<String, String>> readedConfiguration = mainConfigurationReader.getReadedConfiguration();

        //Then
        assertEquals("Living Room 1", readedConfiguration.get("general").get("name"));
        assertEquals("0%", readedConfiguration.get("format").get("humidity"));
        assertEquals("18", readedConfiguration.get("temperature").get("j00"));
        assertEquals("19", readedConfiguration.get("temperature").get("j03"));
        assertEquals("0.5", readedConfiguration.get("humidity").get("j00"));
    }


    //le test vérifie que le constructeur renvoir une exception si le fichier a une extension inconnue ConfigNOTGoodExtention.txt
    @Test
    public void Should_Throw_UnknownArgumentException_When_Extension_Is_Unknown() {
        //Given
        String path = "src/test/resources/ConfigNOTGoodExtension.txt";
        MainConfigurationReader mainConfigurationReader = null;
        //When
        try {
            mainConfigurationReader = new MainConfigurationReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (unknownArgumentException e) {
            //Then
            assertNotNull(e);
        }
    }

    @Test
    public void Should_Throw_UnknownArgumentException_When_Extension_Is_Missing() {
        //Given
        String path = "src/test/resources/A";
        MainConfigurationReader mainConfigurationReader = null;
        //When
        try {
            mainConfigurationReader = new MainConfigurationReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (unknownArgumentException e) {
            //Then
            assertNotNull(e);
        }
    }
}
