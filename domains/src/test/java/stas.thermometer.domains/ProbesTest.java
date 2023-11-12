package stas.thermometer.domains;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ProbesTest {
    //Les tests unitaires suivent une convention de nommage telle que Should…, It…, ou Given...When...Then…



    @Test
    public void AlwaysTRue() {
        assertTrue(true);
    }


    @Test
    public void Should_Return_Null_When_ProbeTemperatureGetMeasurement() {


        List<Double> profil = List.of(7.205, 10.0, 20.0, 1.5, 0.5);

        ProbeTemperature probeTemperature = new ProbeTemperature(profil);

        assertNull(probeTemperature.getMeasurement());
    }

    @Test
    public void Should_Return_Null_When_ProbeHumidityGetMeasurement() {


        List<Double> profil = List.of(0.5,0.02,0.0064,0.209,0.9);

        ProbeHumidity probeHumidity = new ProbeHumidity(profil);

        assertNull(probeHumidity.getMeasurement());
    }


    @Test
    public void Should_Return_Measurement_When_PerfectTiming_Temperature() {


        List<Double> profil = List.of(7.205, 10.0, 20.0, 1.5);
        LocalDateTime date = LocalDateTime.of(2023, 3, 27, 12, 0);

        ProbeTemperature probeTemperature = new ProbeTemperature(profil);

        probeTemperature.generateMeasurement(date);

        Measurement measurement = probeTemperature.getMeasurement();

        assertNotNull(measurement);
        assertTrue(measurement.value() >= 19.5 && measurement.value() <= 20.5);


        assertEquals(date, measurement.dateTime());
        assertEquals(MeasurementType.TEMPERATURE, measurement.type());
    }

    @Test
    public void Should_Return_Measurement_When_PerfectTiming_Humidity() {

        List<Double> profil = List.of(0.5,0.02,0.0064,0.209);
        LocalDateTime date = LocalDateTime.of(2023, 3, 27, 12, 0);

        ProbeHumidity probeHumidity = new ProbeHumidity(profil);

        probeHumidity.generateMeasurement(date);

        Measurement measurement = probeHumidity.getMeasurement();

        assertNotNull(measurement);
        //assertEquals(0.0064, measurement.value());
        // 0.05 de marge d'erreur
        assertTrue(measurement.value() >= -0.0436 && measurement.value() <= 0.0564);
        assertEquals(date, measurement.dateTime());
        assertEquals(MeasurementType.HUMIDITY, measurement.type());
    }

    @Test
    public void Should_Return_Measurement_When_oddProfilNumber_Temperature() {

        List<Double> profil = List.of(7.205, 10.0, 20.0, 1.5, 3.205);
        LocalDateTime date = LocalDateTime.of(2023, 3, 27, 4, 48);

        ProbeTemperature probeTemperature = new ProbeTemperature(profil);

        probeTemperature.generateMeasurement(date);

        Measurement measurement = probeTemperature.getMeasurement();

        assertNotNull(measurement);
        //assertEquals(10, measurement.value());
        // 0.5 de marge d'erreur
        assertTrue(measurement.value() >= 9.5 && measurement.value() <= 10.5);

    }

    @Test
    public void Should_Return_Measurement_When_oddProfilNumber_Humidity() {

        List<Double> profil = List.of(0.5,0.02,0.0064,0.209,0.9);
        LocalDateTime date = LocalDateTime.of(2023, 3, 27, 4, 48);

        ProbeHumidity probeHumidity = new ProbeHumidity(profil);

        probeHumidity.generateMeasurement(date);

        Measurement measurement = probeHumidity.getMeasurement();

        assertNotNull(measurement);
        //assertEquals(0.02, measurement.value());
        // 0.05 de marge d'erreur
        assertTrue(measurement.value() >= -0.03 && measurement.value() <= 0.07);
    }

    @Test
    public void Should_Return_Measurement_When_TimeInTrensition_Temperature() {

        List<Double> profil = List.of(7.205, 10.0, 20.0, 1.5);
        LocalDateTime date = LocalDateTime.of(2023, 3, 27, 9, 0);

        ProbeTemperature probeTemperature = new ProbeTemperature(profil);

        probeTemperature.generateMeasurement(date);

        Measurement measurement = probeTemperature.getMeasurement();

        assertNotNull(measurement);
        //assertEquals(15, measurement.value());
        // 0.5 de marge d'erreur
        assertTrue(measurement.value() >= 14.5 && measurement.value() <= 15.5);
    }

    @Test
    public void Should_Return_Measurement_When_TimeInTrensition_Humidity() {

        List<Double> profil = List.of(0.5,0.02,0.0064,0.209);
        LocalDateTime date = LocalDateTime.of(2023, 3, 27, 9, 0);

        ProbeHumidity probeHumidity = new ProbeHumidity(profil);

        probeHumidity.generateMeasurement(date);

        Measurement measurement = probeHumidity.getMeasurement();

        assertNotNull(measurement);
        //assertEquals(0.0132, measurement.value());
        // 0.05 de marge d'erreur
        assertTrue(measurement.value() >= -0.0368 && measurement.value() <= 0.0632);
    }

    @Test
    public void Should_Return_Measurement_When_Midnight_Temperature() {

        List<Double> profil = List.of(7.205, 10.0, 20.0, 1.5);
        LocalDateTime date = LocalDateTime.of(2023, 3, 27, 21, 0);

        ProbeTemperature probeTemperature = new ProbeTemperature(profil);

        probeTemperature.generateMeasurement(date);

        Measurement measurement = probeTemperature.getMeasurement();

        assertNotNull(measurement);
        //assertEquals(4.3525, measurement.value());
        // 0.5 de marge d'erreur
        assertTrue(measurement.value() >= 3.8525 && measurement.value() <= 4.8525);
    }

    @Test
    public void Should_Return_Measurement_When_Midnight_Humidity() {

        List<Double> profil = List.of(0.5,0.02,0.0064,0.21);
        LocalDateTime date = LocalDateTime.of(2023, 3, 27, 21, 0);

        ProbeHumidity probeHumidity = new ProbeHumidity(profil);

        probeHumidity.generateMeasurement(date);

        Measurement measurement = probeHumidity.getMeasurement();

        assertNotNull(measurement);
        //assertEquals(0.355, measurement.value());
        // 0.05 de marge d'erreur
        assertTrue(measurement.value() >= 0.305 && measurement.value() <= 0.405);
    }

}
