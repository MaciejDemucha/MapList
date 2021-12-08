/*
 * Program: Aplikacja okienkowa z GUI, która umożliwia zarządzanie
 *          grupami obiektów klasy Person.
 *    Plik: GroupOfPeople.java
 *
 *   Autor: Paweł Rogalinski
 *    Data: pazdziernik 2021 r.
 */

package lab1.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lab1.data.MapException;


/*
 * Klasa GroupOfPeople reprezentuje grupy osób, które są opisane za pomocą
 * trzech atrybutow: name, type oraz collection:
 *     name - nazwa grupy wybierana przez użytkownika
 *            (musi zawierać niepusty ciąg znaków).
 *     type - typ kolekcji, która ma być użyta do zapamiętania
 *            danych osób należących do tej grupy.
 *     collection - kolekcja obiektów klasy Person, w której
 *                  pamiętane są dane osób należących do tej grupy.
 *                  (Musi być to obiekt utworzony za pomocą metody
 *                  createCollection z typu wyliczeniowego GroupType).
 */
public class MapGroup implements Iterable<Map>, Serializable{

    private static final long serialVersionUID = 1L;

    private String name;
    private GroupType type;
    private Collection<Map> collection;


    public MapGroup(GroupType type, String name) throws MapException {
        setName(name);
        if (type==null){
            throw new MapException("Nieprawidłowy typ kolekcji.");
        }
        this.type = type;
        collection = this.type.createCollection();
    }


    public MapGroup(String type_name, String name) throws MapException {
        setName(name);
        GroupType type = GroupType.find(type_name);
        if (type==null){
            throw new MapException("Nieprawidłowy typ kolekcji.");
        }
        this.type = type;
        collection = this.type.createCollection();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) throws MapException {
        if ((name == null) || name.equals(""))
            throw new MapException("Nazwa grupy musi być określona.");
        this.name = name;
    }


    public GroupType getType() {
        return type;
    }


    public void setType(GroupType type) throws MapException {
        if (type == null) {
            throw new MapException("Typ kolekcji musi być określny.");
        }
        if (this.type == type)
            return;
        // Gdy następuje zmiana typu kolekcji to osoby zapamiętane
        // w dotychczasowej kolekcji muszą zostać przepisane do nowej
        // kolekcji, która jest implementowana, przy pomocy
        // klasy właściwej dla nowego typu kolekcji.
        Collection<Map> oldCollection = collection;
        collection = type.createCollection();
        this.type = type;
        for (Map map : oldCollection)
            collection.add(map);
    }


    public void setType(String type_name) throws MapException {
        for(GroupType type : GroupType.values()){
            if (type.toString().equals(type_name)) {
                setType(type);
                return;
            }
        }
        throw new MapException("Nie ma takiego typu kolekcji.");
    }


    // Zamiast gettera getCollection zostały zaimplementowane
    // niezbędne metody delegowane z interfejsu Collection,
    // które umożliwiają wykonanie wszystkich operacji na
    // kolekcji osób.

    public boolean add(Map map) {
        return collection.add(map);
    }

    public Iterator<Map> iterator() {
        return collection.iterator();
    }

    public int size() {
        return collection.size();
    }


    // Poniżej zostały zaimplementowane metody umożliwiające
    // sortowanie listy osób według poszczególnych atrybutów.
    // UWAGA: sortowanie jest możliwe tylko dla kolekcji typu Lista.
    public void sortName() throws MapException {
        if (type==GroupType.HASH_SET|| type==GroupType.TREE_SET ){
            throw new MapException("Kolekcje typu SET nie mogą być sortowane.");
        }
        // Przy sortowaniu jako komparator zostanie wykorzystana
        // metoda compareTo będąca implementacją interfejsu
        // Comparable w klasie Person.
        Collections.sort((List<Map>) collection);
    }

    public void sortReleaseYear() throws MapException {
        if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
            throw new MapException("Kolekcje typu SET nie mogą być sortowane.");
        }
        // Przy sortowaniu jako komparator zostanie wykorzystany
        // obiekt klasy anonimowej (klasa bez nazwy), która implementuje
        // interfejs Comparator i zawiera tylko jedną metodę compare.
        Collections.sort((List<Map>) collection, (o1, o2) -> {
            if (o1.getReleaseYear() < o2.getReleaseYear())
                return -1;
            if (o1.getReleaseYear() > o2.getReleaseYear())
                return 1;
            return 0;
        });
    }

    public void sortType() throws MapException {
        if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
            throw new MapException("Kolekcje typu SET nie mogą być sortowane.");
        }
        // Przy sortowaniu jako komparator zostanie wykorzystany
        // obiekt klasy anonimowej (klasa bez nazwy), która implementuje
        // interfejs Comparator i zawiera tylko jedną metodę compare.
        Collections.sort((List<Map>) collection, Comparator.comparing(o -> o.getMapType().toString()));
    }


    @Override
    public String toString() {
        return name + "  [" + type + "]";
    }


    public static void printToFile(PrintWriter writer, MapGroup group) {
        writer.println(group.getName());
        writer.println(group.getType());
        for (Map map : group.collection)
            Map.printToFile(writer, map);
    }


    public static void printToFile(String file_name, MapGroup group) throws MapException {
        try (PrintWriter writer = new PrintWriter(file_name)) {
            printToFile(writer, group);
        } catch (FileNotFoundException e){
            throw new MapException("Nie odnaleziono pliku " + file_name);
        }
    }


    public static MapGroup readFromFile(BufferedReader reader) throws MapException{
        try {
            String group_name = reader.readLine();
            String type_name = reader.readLine();
            MapGroup groupOfPeople = new MapGroup(type_name, group_name);

            Map map;
            while((map = Map.readFromFile(reader)) != null)
                groupOfPeople.collection.add(map);
            return groupOfPeople;
        } catch(IOException e){
            throw new MapException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }


    public static MapGroup readFromFile(String file_name) throws MapException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
            return MapGroup.readFromFile(reader);
        } catch (FileNotFoundException e){
            throw new MapException("Nie odnaleziono pliku " + file_name);
        } catch(IOException e){
            throw new MapException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }


    //#######################################################################
    //#######################################################################
    //
    // Poniżej umieszczono cztery pomocnicze metody do tworzenia specjalnych
    // grup, które są wynikiem wykonania wybranych operacji na dwóch grupach
    // żródłowych. Możliwe są następujące operacje:
    //   SUMA  - grupa osób zawierająca wszystkie osoby z grupy pierwszej
    //           oraz wszystkie osoby z grupy drugiej;
    //   ILICZYN - grupa osób, które należą zarówno do grupy pierwszej jak i
    //             do grupy drugiej;
    //   RÓŻNICA - grupa osób, które należą do grupy pierwszej
    //             i nie ma ich w grupie drugiej
    //   RÓŻNICA SYMETRYCZNA - grupa osób, które należą do grupy pierwszej
    //             i nie ma ich w grupie drugiej oraz te osoby, które należą
    //             do grupy drugiej i nie ma w grupie pierwszej.
    //
    //   Nazwa grupy specjalnej jest tworzona według następującego wzorca"
    //          ( nazwa1 NNN nazwa2 )
    //   gdzie
    //         nazwa1 - nazwa pierwszej grupy osób,
    //         nazwa2 - nazwa drugiej grupy osób,
    //         NNN - symbol operacji wykonywanej na grupach osób:
    //                   "OR"  - dla operacji typu SUMA,
    //                   "AND" - dla operacji typu ILOCZYN,
    //                   "SUB" - dla operacji typu Różńica,
    //                   "XOR" - dla operacji typu RóżNICA SYMETRYCZNA.
    //
    //   Typ grupy specjalnej zależy od typu grup żródłowych i jest wybierany
    //   według następujących reguł:
    //  	 - Jeśli obie grupy żródłowe są tego samego rodzaju (lista lub zbiór)
    //  	   to grupa wynikpwa ma taki typ jak pierwsza grupa żródłowa.
    //       - Jeśli grupy żródłowe różnią się rodzajem (jedna jest listą, a druga zbioerm)
    //         to grupa wynikowa ma taki sam typ jak grupa żródłowa, która jest zbiorem.
    //
    //   Ilustruje to poniższa tabelka
    //       |=====================================================================|
    //       |   grupy żródłowe    |   grupa  |  uwagi dodatkowe                   |
    //       | pierwsza |  druga   | wynikowa |                                    |
    //       |==========|==========|==========|====================================|
    //       |  lista   |  lista   |   lista  | typ dziedziczony z grupy pierwszej |
    //       |  lista   |  zbiór   |   zbiór  | typ dziedziczony z grupy drugiej   |
    //       |  zbiór   |  lista   |   lista  | typ dziedziczony z grupy pierwszej |
    //       |  zbiór   |  zbiór   |   zbiór  | typ dziedziczony z grupy pierwszej |
    //       =======================================================================
    //
    //##################################################################################
    //##################################################################################

    /*public static GroupOfPeople createGroupUnion(GroupOfPeople g1,GroupOfPeople g2) throws PersonException {
        String name = "(" + g1.name + " OR " + g2.name +")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
            type = g2.type;
        } else {
            type = g1.type;
        }
        GroupOfPeople group = new GroupOfPeople(type, name);
        group.collection.addAll(g1.collection);
        group.collection.addAll(g2.collection);
        return group;
    }

    public static GroupOfPeople createGroupIntersection(GroupOfPeople g1,GroupOfPeople g2) throws PersonException {
        String name = "(" + g1.name + " AND " + g2.name +")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
            type = g2.type;
        } else {
            type = g1.type;
        }
        GroupOfPeople group = new GroupOfPeople(type, name);

        Collection<Person> tmp_g2 = group.type.createCollection();
        tmp_g2.addAll(g2.collection);
        for(Person person : g1.collection){
            if(tmp_g2.remove(person)){
                group.collection.add(person);
            }
        }
        return group;
    }

    public static GroupOfPeople createGroupDifference(GroupOfPeople g1,GroupOfPeople g2) throws PersonException {
        String name = "(" + g1.name + " SUB " + g2.name +")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
            type = g2.type;
        } else {
            type = g1.type;
        }
        GroupOfPeople group = new GroupOfPeople(type, name);
        group.collection.addAll(g1.collection);
        for(Person person : g2.collection){
            group.collection.remove(person);
        }
        return group;
    }


    public static GroupOfPeople createGroupSymmetricDiff(GroupOfPeople g1,GroupOfPeople g2) throws PersonException {
        String name = "(" + g1.name + " XOR " + g2.name +")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
            type = g2.type;
        } else {
            type = g1.type;
        }
        // Oblicz różnicę g2 - g1
        Collection<Person> tmp_sub = type.createCollection();
        tmp_sub.addAll(g2.collection);
        for(Person person : g1.collection){
            tmp_sub.remove(person);
        }
        // Oblicz różnice g1 - g2
        GroupOfPeople group = new GroupOfPeople(type, name);
        group.collection.addAll(g1.collection);
        for(Person person : g2.collection){
            group.collection.remove(person);
        }
        // Do różnicy (g1-g2)  dodaj różnice (g2-g1)
        group.collection.addAll(tmp_sub);
        return group;
    }*/

} // koniec klasy GroupOfPeople
