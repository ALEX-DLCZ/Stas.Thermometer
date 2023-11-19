package stas.thermometer.domains;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ThermometerRepositoryInterface {

    Map<String, String> getFormat();

    void notifyUpdate();

    List<AggregatorMain> getAggregators();

}
