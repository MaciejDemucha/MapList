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

public class Map {
    private String name;
    private MapType mapType;
    private String land;
    private float price;
    private int releaseYear;

    public Map(String name, MapType mapType, String land, float price, int releaseYear) {
        this.name = name;
        this.mapType = mapType;
        this.land = land;
        this.price = price;
        this.releaseYear = releaseYear;
    }

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

    public void setReleaseYear(int releaseDate) throws MapException {
        if(releaseDate > 2021 || releaseDate < 0)
            throw new MapException("Data wydania musi być w przedziale 0 - 2021!");
        this.releaseYear = releaseDate;
    }

    public void displayMapInfo(){
        System.out.println("Nazwa: " + getName());
        System.out.println("Rodzaj: " + getMapType());
        System.out.println("Obszar: " + getLand());
        System.out.println("Cena: " + getPrice() + " zł");
        System.out.println("Rok wydania: " + getReleaseYear());
    }

    @Override
    public String toString(){
        return name + " " + land;
    }
}
