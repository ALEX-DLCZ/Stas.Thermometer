package stas.thermometer.infrastructures.database;

public class Alert {
    private final double expectedValue;


    private final int idMesure;

    public Alert( double expectedValue, int idMesure) {
        this.expectedValue = expectedValue;
        this.idMesure = idMesure;
    }


    //--------------------------

    public int getIdMesure() {
        return idMesure;
    }
    public double getExpectedValue() {
        return this.expectedValue;
    }

}
