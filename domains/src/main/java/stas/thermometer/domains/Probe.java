package stas.thermometer.domains;

import java.time.LocalDateTime;

public interface Probe {

     void generateMeasurement(LocalDateTime dateTime);
     Measurement getMeasurement();

}
