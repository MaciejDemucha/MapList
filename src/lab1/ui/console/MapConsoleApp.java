/*
 * Program: Program testujący operacje na obiektach klasy Map reprezentujących różne rodzaje map
 *
 *       Plik: MapConsoleApp.java
 *
 *          Aplikacja konsolowa pozwalająca przetestować poszczególne operacje na obiektach klasy Map zawartych
 *          w ArrayList maps.
 *
 *   Autor: Maciej Demucha 259111
 *    Data: 28 pazdziernik 2021 r.
 *
 */

package lab1.ui.console;

import lab1.data.MapException;
import lab1.data.MapList;
import java.util.Scanner;

public class MapConsoleApp {

    private static final String AUTHOR =
            "Języki programowania - Laboratorium 1\n" +
            "Program: Map - wersja konsolowa\n" +
            "Autor: Maciej Demucha 259111\n" +
            "Data: 28 październik 2021 r.\n";

    public static void main(String[] args) {
        System.out.println("\n" + AUTHOR);
        run();
    }

    public static void run(){
        Scanner scanner = new Scanner(System.in);
        boolean shouldContinue = true;
        int choice;
        ConsoleUserDialog ui = new ConsoleUserDialog(); //zestaw metod do aplikacji konsolowej
        MapList maps = new MapList();        // Klasa przechowująca Arraylist obiektów typu Map i
                                            //  metody wykonujące działania na tych obiektach

        while(shouldContinue) {

            ui.clearConsole();

            System.out.println("Wybierz działanie: ");
            System.out.println("1. Wyświetl listę");
            System.out.println("2. Dodaj mapę");
            System.out.println("3. Wyczyść listę");
            System.out.println("4. Usuń mapę");
            System.out.println("5. Zmień dane");
            System.out.println("6. Zapisz dane do pliku");
            System.out.println("7. Wczytaj dane z pliku");
            System.out.println("8. Zakończ");

            choice = scanner.nextInt();

            try{
                switch (choice) {
                    case 1 -> maps.displayMaps();       //Wyświetlenie listy map
                    case 2 -> maps.addMap();            //Dodanie mapy do listy
                    case 3 -> maps.clearList();         //Wyczyszczenie listy
                    case 4 -> maps.deleteElement();     //Usunięcie wybranej mapy
                    case 5 -> maps.changeData();        //Zmiana danych wybranej mapy
                    case 6 -> {
                        //Zapis danych wybranej mapy do pliku tekstowego
                        String file_name = ui.enterString("Podaj nazwę pliku (podaj rozszerzenie): ");
                        MapList.printToFile(file_name);
                        ui.printInfoMessage("Zapisano dane mapy do pliku tekstowego");
                    }
                    case 7 -> {
                        //Pobranie danych z pliku tekstowego i dodanie mapy do listy
                        String file_name = ui.enterString("Podaj nazwę pliku: ");
                        maps.add(MapList.readFromFile(file_name));
                        ui.printInfoMessage("Dodano mapę z pliku tekstowego.");
                    }
                    case 8 -> {
                        scanner.close();
                        shouldContinue = false;   //Zakończenie programu
                    }
                    default -> System.out.println("Zły numer!");
                }
            }
            catch (MapException e){
                ui.printErrorMessage(e.getMessage());
            }
        }

    }

}
