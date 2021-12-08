/*
 * Program: Aplikacja okienkowa z GUI, która umożliwia zarządzanie
 *          grupami obiektów klasy Person.
 *    Plik: GroupType.java
 *
 *   Autor: Paweł Rogalinski
 *    Data: pazdziernik 2021 r.
 */

package lab1.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Vector;



/*
 *  Typ wyliczeniowy GroupType reprezentuje typy kolekcji,
 *  które mogą być wykorzystane do tworzenia grupy osób.
 *  w programie można wybrać dwa rodzaje kolekcji: listy i zbiory.
 *  Każdy rodzaj kolekcji może być implementowany przy pomocy
 *  różnych klas:
 *      Listy: klasa Vector, klasa ArrayList, klasa LinnkedList;
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
    // tworzy kolekcję obiektów klasy Person implementowaną
    // za pomocą właściwej klasy z pakietu Java Collection Framework.
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
