/*
 * Program: Aplikacja okienkowa z GUI, która umożliwia zarządzanie
 *          grupami obiektów klasy Person.
 *    Plik: GroupType.java
 *
 *   Autor: Maciej Demucha
 *    Data: 9 grudnia 2021 r.
 */

package lab1.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Vector;



/*
 *  Enum GroupType reprezentuje rodzaje kolekcji służących do
 *  grupowania obiektów typu Map
 *      Listy: klasa Vector, klasa ArrayList, klasa LinkedList;
 *     Zbiory: klasa TreeSet, klasa HashSet.
 */
public enum GroupType {
    VECTOR("Lista   (klasa Vector)"),
    ARRAY_LIST("Lista   (klasa ArrayList)"),
    LINKED_LIST("Lista   (klasa LinkedList)"),
    HASH_SET("Zbiór   (klasa HashSet)"),
    TREE_SET("Zbiór   (klasa TreeSet)");

    String typeName;

    private GroupType(String type_name) {
        typeName = type_name;
    }


    @Override
    public String toString() {
        return typeName;
    }


    public static GroupType find(String typeName){
        for(GroupType type : values()){
            if (type.typeName.equals(typeName)){
                return type;
            }
        }
        return null;
    }


    // Metoda createCollection() dla wybranego typu grupy
    // tworzy kolekcję obiektów klasy Map
    public Collection<Map> createCollection() throws MapException {
        switch (this) {
            case VECTOR:      return new Vector<Map>();
            case ARRAY_LIST:  return new ArrayList<Map>();
            case HASH_SET:    return new HashSet<Map>();
            case LINKED_LIST: return new LinkedList<Map>();
            case TREE_SET:    return new TreeSet<Map>();
            default:          throw new MapException("Podany typ kolekcji nie został zaimplementowany.");
        }
    }

}  // koniec klasy enum GroupType
