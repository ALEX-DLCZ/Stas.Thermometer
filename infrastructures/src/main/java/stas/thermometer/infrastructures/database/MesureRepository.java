package stas.thermometer.infrastructures.database;


public interface MesureRepository extends DataMapper<Mesure>{
    int getMesureId(Mesure mesure);
}
