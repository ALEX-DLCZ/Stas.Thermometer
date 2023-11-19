package stas.thermometer.domains;

import java.util.HashMap;
import java.util.Map;

public interface ThermometerRepositoryInterface {

    Map<String, String> getFormat();

    void notifyUpdate();

}
