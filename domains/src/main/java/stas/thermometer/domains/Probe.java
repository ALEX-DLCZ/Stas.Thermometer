package stas.thermometer.domains;

import java.time.LocalDateTime;

public interface Probe {

     String getName();

     Measurement generateMeasurement(LocalDateTime dateTime);

}
