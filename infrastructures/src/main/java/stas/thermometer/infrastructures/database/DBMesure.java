package stas.thermometer.infrastructures.database;

public class DBMesure extends DBDataMapper<Mesure> implements MesureRepository{

    public DBMesure(String connectionString) {
        super(connectionString,"Mesures" ,Mesure.class);
    }

    @Override
    public int getMesureId() {
        //objRefMap
        return 0;
    }

}
