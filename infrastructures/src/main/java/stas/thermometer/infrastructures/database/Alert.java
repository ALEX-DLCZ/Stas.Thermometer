package stas.thermometer.infrastructures.database;

public class Alert {
    private int idMesure;
    private double expectedValue;

    public Alert( double expectedValue) {
        this.expectedValue = expectedValue;
    }


    //--------------------------

    public int getIdMesure() {
        return idMesure;
    }
    public void setIdMesure(int idMesure) {
        this.idMesure = idMesure;
    }

    public double getExpectedValue() {
        return expectedValue;
    }
    public void setExpectedValue(double expectedValue) {
        this.expectedValue = expectedValue;
    }
}
