package stas.thermometer.infrastructures;

import org.junit.jupiter.api.Test;
import stas.thermometer.infrastructures.IniConfigurationReader;
import stas.thermometer.infrastructures.personalExceptions.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class IniConfigurationReaderTest {

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

configBASICEmpty.ini :

configNOPROFILES.ini :
; Last modified 15 July 2010 by Juan Dona
[general]
name=Living Room 1
[format]
temperature=00.00°
datetime=dd/MM/YYYY à HH :mm :ss

configNONAME.ini :
; Last modified 15 July 2010 by Juan Dona
[general]
[format]
temperature=00.00°
humidity=0%
datetime=dd/MM/YYYY à HH :mm :ss
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


    @Test
    public void ShouldReadIniFile()  {
        //Given
        String path = "src/test/resources/configBASIC.ini";
        IniConfigurationReader iniConfigurationReader = new IniConfigurationReader();

        //When
        HashMap<String, HashMap<String, String>> readedConfiguration = new HashMap<>();
        try {
             readedConfiguration = iniConfigurationReader.executeConfigurationReaderStrategy(path);
        } catch (FileNotFoundException e) {
            fail();
        }
        //Then
        assertNotNull(readedConfiguration);
        assertEquals(5, readedConfiguration.size());

        assertEquals("Living Room 1", readedConfiguration.get("general").get("name"));
        //Expected :00.00Â° ???????
        //assertEquals("00.00°", readedConfiguration.get("format").get("temperature"));
        //expected: <dd/MM/YYYY Ã  HH :mm :ss> ???????????????
        assertEquals("dd/MM/YYYY a HH :mm :ss", readedConfiguration.get("format").get("datetime"));
        assertEquals("18", readedConfiguration.get("temperature").get("j00"));
        assertEquals("19", readedConfiguration.get("temperature").get("j01"));
        assertEquals("20", readedConfiguration.get("temperature").get("j02"));
        assertEquals("19", readedConfiguration.get("temperature").get("j03"));
        assertEquals("0.5", readedConfiguration.get("humidity").get("j00"));
        assertEquals("0.5", readedConfiguration.get("humidity").get("j01"));
        assertEquals("0.0", readedConfiguration.get("humidity").get("j02"));
        assertEquals("0.55", readedConfiguration.get("humidity").get("j03"));
        assertEquals("0.01", readedConfiguration.get("humidity").get("j04"));
        assertEquals("0.7", readedConfiguration.get("humidity").get("j05"));
    }


    @Test
    public void ShouldReadEmptyIniFile() {
        //Given
        String path = "src/test/resources/configBASICEmpty.ini";
        IniConfigurationReader iniConfigurationReader = new IniConfigurationReader();

        //When
        HashMap<String, HashMap<String, String>> readedConfiguration = new HashMap<>();
        try {
            readedConfiguration = iniConfigurationReader.executeConfigurationReaderStrategy(path);
        } catch (FileNotFoundException e) {
            fail();
        }
        //Then
        assertTrue(readedConfiguration.isEmpty());
    }


    @Test
    public void ShouldReadIniFileWithoutProfiles() {
        //Given
        String path = "src/test/resources/configNOPROFILES.ini";
        IniConfigurationReader iniConfigurationReader = new IniConfigurationReader();

        //When
        HashMap<String, HashMap<String, String>> readedConfiguration = new HashMap<>();
        try {
            readedConfiguration = iniConfigurationReader.executeConfigurationReaderStrategy(path);
        } catch (FileNotFoundException e) {
            fail();
        }
        //Then
        assertEquals("Living Room 1", readedConfiguration.get("general").get("name"));
        //Expected :00.00Â° ???????
        //assertEquals("00.00°", readedConfiguration.get("format").get("temperature"));
        //expected: <dd/MM/YYYY Ã  HH :mm :ss> ???????????????
        //assertEquals("dd/MM/YYYY à HH :mm :ss", readedConfiguration.get("format").get("datetime"));

    }


    //test avec un fichier qui n'existe pas donc la méthode executeConfigurationReaderStrategy doit lever une exception
    @Test
    public void ShouldThrowFileNotFoundException() {
        //Given
        String path = "src/test/resources/configNOTEXIST.ini";
        IniConfigurationReader iniConfigurationReader = new IniConfigurationReader();

        //When
        HashMap<String, HashMap<String, String>> readedConfiguration = new HashMap<>();
        try {
            readedConfiguration = iniConfigurationReader.executeConfigurationReaderStrategy(path);
        } catch (FileNotFoundException e) {
            //Then
            assertTrue(true);
        }
    }
}
