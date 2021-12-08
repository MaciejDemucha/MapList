/*
 * Program: Program testujący operacje na obiektach klasy Map reprezentujących różne rodzaje map
 *
 *       Plik: Map.java
 *
 *          Obiekty klasy Map reprezentują mapy, które posiadają atrybuty:
 *          nazwa (niepusty ciąg znaków),
 *          rodzaj (typ wyliczeniowy MapType),
 *          reprezentowany obszar (niepusty ciąg znaków),
 *          cena (musi być większe od zera),
 *          rok wydania (musi być w przedziale 0 - 2021)
 *
 *   Autor: Maciej Demucha 259111
 *    Data:  28 pazdziernik 2021 r.
 *
 */

package lab1.data;

import java.io.*;

public class Map implements Serializable, Comparable {
    private String name; //Nazwa
    private MapType mapType; //Rodzaj mapy
    private String land;    //Przedstawiany obszar
    private float price;    //Cena
    private int releaseYear; //Rok wydania
    //konstruktor
    public Map(String name, MapType mapType, String land, float price, int releaseYear) {
        this.name = name;
        this.mapType = mapType;
        this.land = land;
        this.price = price;
        this.releaseYear = releaseYear;
    }
    //gettery
    public String getName() {
        return name;
    }

    public MapType getMapType() {
        return mapType;
    }

    public String getLand() {
        return land;
    }

    public float getPrice() {
        return price;
    }

    public int getReleaseYear() {
        return releaseYear;
    }
    //settery
    public void setName(String name) throws MapException {
        if((name == null) || name.equals(""))
            throw new MapException("Nazwa nie może być pusta!");
        this.name = name;
    }

    public void setMapType(String mapType) throws MapException {
        if (mapType == null || mapType.equals("")){
            this.mapType = MapType.UNKNOWN;
            return;
        }
        for (MapType map_Type : MapType.values()) {
            if(map_Type.typeName.equals(mapType)) {
                this.mapType = map_Type;
                return;
            }
        }
        throw new MapException("Nie ma takiego rodzaju mapy!");
    }

    public void setMapType(MapType mapType){
        this.mapType = mapType;
    }

    public void setLand(String land) throws MapException {
        if((name == null) || name.equals(""))
            throw new MapException("Nazwa nie może być pusta!");
        this.land = land;
    }

    public void setPrice(float price) throws MapException {
        if(price <= 0)
            throw new MapException("Cena musi być większa od zera!");
        this.price = price;
    }

    public void setPrice(String price) throws MapException {
        if (price == null || price.equals("")){
            setPrice(0);
            return;
        }
            setPrice(Float.parseFloat(price));
    }

    public void setReleaseYear(int releaseYear) throws MapException {
        if(releaseYear > 2021 || releaseYear < 0)
            throw new MapException("Data wydania musi być w przedziale 0 - 2021!");
        this.releaseYear = releaseYear;
    }

    public void setReleaseYear(String releaseYear) throws MapException {
        if (releaseYear == null || releaseYear.equals("")){
            setReleaseYear(0);
            return;
        }
        try {
            setReleaseYear(Integer.parseInt(releaseYear));
        } catch (NumberFormatException e) {
            throw new MapException("Data wydania musi być liczbą całkowitą.");
        }
    }
    //Wyświetlenie informacji o mapie
    public void displayMapInfo(){
        System.out.println("Nazwa: " + getName());
        System.out.println("Rodzaj: " + getMapType());
        System.out.println("Obszar: " + getLand());
        System.out.println("Cena: " + getPrice() + " zł");
        System.out.println("Rok wydania: " + getReleaseYear());
    }
    //Metody do zad 3
/*
    public static void printToFileBinary(PrintWriter writer,Map map){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("map.bin"))) {
            outputStream.writeObject(map);

            writer.println(map.name + "#" + map.mapType +
                    "#" + map.land + "#" + map.price + "#" + map.releaseYear);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printToFileBinary(String file_name, Map map) throws MapException {
        try (PrintWriter writer = new PrintWriter(file_name)) {
            printToFileBinary(writer, map);
        } catch (FileNotFoundException e){
            throw new MapException("Nie odnaleziono pliku " + file_name);
        }
    }

    public static Map readFromFileBinary(Map map){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("map.bin"))) {
            map = (Map) inputStream.readObject();
            return map;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map readFromFileBinary(String file_name) throws MapException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
            return Map.readFromFile(reader);
        } catch (FileNotFoundException e){
            throw new MapException("Nie odnaleziono pliku " + file_name);
        } catch(IOException e){
            throw new MapException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }
*/
    //Zapisanie do pliku
    public static void printToFile(PrintWriter writer, Map map){
        writer.println(map.name + "#" + map.mapType +
                "#" + map.land + "#" + map.price + "#" + map.releaseYear);
    }

    public static void printToFile(String file_name, Map map) throws MapException {
        try (PrintWriter writer = new PrintWriter(file_name)) {
            printToFile(writer, map);
        } catch (FileNotFoundException e){
            throw new MapException("Nie odnaleziono pliku " + file_name);
        }
    }

//Wczytanie z pliku
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
            return Map.readFromFile(reader);
        } catch (FileNotFoundException e){
            throw new MapException("Nie odnaleziono pliku " + file_name);
        } catch(IOException e){
            throw new MapException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }

    @Override
    public String toString(){
        return name + " " + land;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    //@Override
   // public int compareTo(Map o) {

           // if (getName() == null || o.getName() == null) {
              //  return 0;
           // }
          //  return getName().compareTo(o.getName());
      //  }


}
