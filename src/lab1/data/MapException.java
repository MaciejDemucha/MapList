/*
 * Program: Program testujący operacje na obiektach klasy Map reprezentujących różne rodzaje map
 *
 *       Plik: MapException.java
 *
 *          Pomocnicza klasa zawierająca metodę służącą do wyświetlania komunikatów
 *          podczas operacji na obiektach klasy Map.
 *
 *   Autor: Maciej Demucha 259111
 *    Data:  28 pazdziernik 2021 r.
 *
 */



package lab1.data;

public class MapException extends Exception{
    private static final long serialVersionUID = 1L;

    public MapException(String message) {
        super(message);
    }
}
