package stas.thermometer.domains;

import java.time.LocalDateTime;

public interface Probe {

     String getName();

     void generateMeasurement(LocalDateTime dateTime);
     Measurement getMeasurement();

}
