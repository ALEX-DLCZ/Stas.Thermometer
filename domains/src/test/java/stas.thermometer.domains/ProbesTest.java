package stas.thermometer.domains;

import org.junit.jupiter.api.Test;
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

        // 15.0,9.4,25.46,1,-0.02,10.0

        List<Double> profil = List.of(15.0, 9.4, 25.46, 1.0, -0.02, 10.0);

        ProbeTemperature probeTemperature = new ProbeTemperature(profil);

        assertNull(probeTemperature.getMeasurement());
    }

    @Test
    public void Should_Return_Null_When_ProbeHumidityGetMeasurement() {

        // 15.0,9.4,25.46,1,-0.02,10.0

        List<Double> profil = List.of(15.0, 9.4, 25.46, 1.0, -0.02, 10.0);

        ProbeHumidity probeHumidity = new ProbeHumidity(profil);

        assertNull(probeHumidity.getMeasurement());
    }

}
