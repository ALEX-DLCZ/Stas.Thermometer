package stas.thermometer.domains;

import org.junit.jupiter.api.Test;

public class MeasurementTypeTest {

    /**
     * UNIQUEMENT POUR LE 100% COVERAGE ...
     * oui ces tests sont inutiles mais on ne sait jamais
     */

    @Test
    public void testMeasurementType() {
        MeasurementType type = MeasurementType.TEMPERATURE;
        assert(type.getType().equals("temperature"));
    }

    @Test
    public void testMeasurementType2() {
        MeasurementType type = MeasurementType.HUMIDITY;
        assert(type.getType().equals("humidity"));
    }

}
