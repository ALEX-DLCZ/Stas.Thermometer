package stas.thermometer.domains;

import java.time.LocalDateTime;

public interface Probe {

     Measurement generateMeasurement(LocalDateTime dateTime);

}
