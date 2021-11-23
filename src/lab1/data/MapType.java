/*
 * Program: Program testujący operacje na obiektach klasy Map reprezentujących różne rodzaje map
 *
 *       Plik: MapType.java
 *
 *          Typ wyliczeniowy zawierający ciągi znaków reprezentujące różne rodzaje map.
 *
 *   Autor: Maciej Demucha 259111
 *    Data: 28 pazdziernik 2021 r.
 *
 */



package lab1.data;

public enum MapType {
    TOPOGRAPHIC("Topograficzna"),
    DEMOGRAPHIC("Demograficzna"),
    ECONOMIC("Ekonomiczna"),
    POLITICAL("Polityczna"),
    CARMAP("Samochodowa"),
    UNKNOWN("Nieznany");

    String typeName;
    MapType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString(){
        return typeName;
    }
}
