package stas.thermometer.domains;

import org.junit.jupiter.api.Test;

public class ValueTypeTest {

    /**
     * UNIQUEMENT POUR LE 100% COVERAGE ...
     * oui ces tests sont inutiles mais on ne sait jamais
     */

    @Test
    public void testMeasurementType() {
        ValueType type = ValueType.TEMPERATURE;
        assert(type.getType().equals("temperature"));
        assert (type.getProbeClass().equals(ProbeTemperature.class));

    }

    @Test
    public void testMeasurementType2() {
        ValueType type = ValueType.HUMIDITY;
        assert(type.getType().equals("humidity"));
        assert (type.getProbeClass().equals(ProbeHumidity.class));
    }

}
