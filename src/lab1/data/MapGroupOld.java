/*
        * Program: Program testujący operacje na obiektach klasy Map reprezentujących różne rodzaje map
        *
        *       Plik: MapList.java
        *
        *          Klasa zawierająca listę obiektów typu Map oraz metody pozwalająca na modyfikowanie poszczególnych danych.
        *
        *   Autor: Maciej Demucha 259111
        *    Data: 28 pazdziernik 2021 r.
        *
*/

package lab1.data;

import lab1.ui.console.ConsoleUserDialog;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MapGroupOld {
    private static final List<Map> maps = new ArrayList<>(); //Przechowywanie obiektów Map
    Scanner scanner = new Scanner(System.in);
    static ConsoleUserDialog ui = new ConsoleUserDialog();
    //Dodanie mapy do arraylist i ustawienie jej parametrów
    public void addMap() throws MapException {
        Map map = new Map(" ", MapType.UNKNOWN, " ", 0, 0);

        map.setName(ui.enterString("Podaj nazwę: "));
        ui.printMessage("Rodzaje map:" + Arrays.deepToString(MapType.values()));
        map.setMapType(ui.enterString("Podaj rodzaj mapy: "));
        map.setLand(ui.enterString("Podaj obszar: "));
        map.setPrice(ui.enterFloat("Podaj cenę: "));
        map.setReleaseYear(ui.enterInt("Podaj rok wydania: "));

    }
    //Sprawdzenie czy jest pusta grupa
    public boolean isMapsEmpty(){
        return maps.isEmpty();
    }
    //Zwrócenie mapy na indeksie
    public Map getMap(int index){
        return maps.get(index);
    }
    //Zwrócenie rozmiaru grupy
    public int getSize(){
        return maps.size();
    }
    //Dodanie określonej mapy z ustalonymi danymi
    public void add(Map map){
        maps.add(map);
    }
    //Wyświetlenie grupy w konsoli
    public void displayMaps(){
        if(maps.isEmpty()) {
            ui.printInfoMessage("Lista jest pusta");
        }
        else {
            int i = 1;
            for (Map map : maps) {
                System.out.println("\n" + i + ".");
                i++;
                map.displayMapInfo();
            }
        }
    }
    //Zmiana danych
    public void changeData() throws MapException {
        if(maps.isEmpty()){
            ui.printInfoMessage("Lista jest pusta");
        }
        else {
            int listNumber;
            int change;
            listNumber = ui.enterInt("Podaj numer z listy mapy, której dane chcesz zmodyfikować: ");
            System.out.println("Co chcesz zmienić: ");
            System.out.println("1. Nazwa ");
            System.out.println("2. Rodzaj mapy ");
            System.out.println("3. Obszar ");
            System.out.println("4. Cenę ");
            System.out.println("5. Rok wydania ");
            change = scanner.nextInt();
            Map map = maps.get(listNumber - 1);

            switch (change) {
                case 1 -> map.setName(ui.enterString("Podaj nazwę: "));
                case 2 -> {
                    ui.printMessage("Rodzaje map:" + Arrays.deepToString(MapType.values()));
                    map.setMapType(ui.enterString("Podaj rodzaj mapy: "));
                }
                case 3 -> map.setLand(ui.enterString("Podaj obszar: "));
                case 4 -> map.setPrice(ui.enterFloat("Podaj cenę: "));
                case 5 -> map.setReleaseYear(ui.enterInt("Podaj rok wydania: "));
            }
        }
    }
    //Usuniecie pojedyńczego elementu w konsolowej wersji
    public void deleteElementConsole() throws MapException {
        if(maps.isEmpty()) {
            ui.printInfoMessage("Lista jest pusta");
        }
        else {
            try{
                int listNumber;
                listNumber = ui.enterInt("Podaj numer z listy mapy, którą chcesz usunąć: ");
                maps.remove(listNumber - 1);
                ui.printInfoMessage("Mapa o numerze " + listNumber + " została usunięta z listy.");
            }
            catch (IndexOutOfBoundsException e){
                throw new MapException("Nie ma zapisanej mapy na podanym indeksie!");
            }
        }
    }
    //Usunięcie elementu w okienkowej aplikacji
    public void removeMap(int index){
        maps.remove(index);
    }
    //Wyczyszczenie grupy
    public void clearList(){
        maps.clear();
    }
    //Zapis podanej mapy do pliku w konsolowej aplikacji
    public static void printToFile(PrintWriter writer) throws MapException {
        try{
            int listNumber;
            listNumber = ui.enterInt("Podaj numer z listy mapy, której dane chcesz zapisać do pliku tekstowego: ");
            Map map = maps.get(listNumber - 1);
            writer.println(map.getName() + "#" + map.getMapType() +
                    "#" + map.getLand() + "#" + map.getPrice() + "#" + map.getReleaseYear());
        }
        catch (IndexOutOfBoundsException e){
            throw new MapException("Nie ma zapisanej mapy na podanym indeksie!");
        }
    }

    public static void printToFile(String file_name) throws MapException {
        try (PrintWriter writer = new PrintWriter(file_name)) {
            printToFile(writer);
        } catch (FileNotFoundException e){
            throw new MapException("Nie odnaleziono pliku " + file_name);
        }
    }
    //Odczytanie mapy z pliku i dodanie go do grupy
    public static Map readFromFile(BufferedReader reader) throws MapException{
        try {
            String line = reader.readLine();
            String[] txt = line.split("#");
            Map map = new Map(txt[0], MapType.UNKNOWN, txt[2], 0, 0);

            map.setMapType(txt[1]);
            map.setPrice(Float.parseFloat(txt[3]));
            map.setReleaseYear(Integer.parseInt(txt[4]));

            return map;
        } catch(IOException e){
            throw new MapException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }

    public static Map readFromFile(String file_name) throws MapException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
            return MapGroupOld.readFromFile(reader);
        } catch (FileNotFoundException e){
            throw new MapException("Nie odnaleziono pliku " + file_name);
        } catch(IOException e){
            throw new MapException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }
}
