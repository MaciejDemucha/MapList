/*
 * Program: Program testujący operacje na obiektach klasy Map reprezentujących różne rodzaje map
 *
 *       Plik: MapGroupWindowApp.java
 *
 *          Aplikacja okienkowa pozwalająca przetestować poszczególne operacje na obiektach klasy Map zawartych
 *          w ArrayList maps.
 *          WERSJA DLA WIELU MAP
 *
 *   Autor: Maciej Demucha 259111
 *    Data: 29 listopad 2021 r.
 *
 */

package lab1.ui.gui;

import lab1.data.Map;
import lab1.data.MapException;
import lab1.data.MapGroupOld;
import lab1.data.MapType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapGroupWindowAppOld extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    int mapIndex = 0;
    private MapGroupOld maps = new MapGroupOld();
    private Map currentMap;
    private Map toAdd = new Map("ads", MapType.ECONOMIC, "polska", 20, 2000);


    Font font = new Font("MonoSpaced", Font.BOLD, 12);

    JLabel indexLabel        = new JLabel("              Index: ");
    JLabel nameLabel        = new JLabel("               Nazwa: ");
    JLabel mapTypeLabel     = new JLabel("         Rodzaj mapy: ");
    JLabel landLabel        = new JLabel("Przedstawiony obszar: ");
    JLabel priceLabel       = new JLabel("                Cena: ");
    JLabel releaseYearLabel = new JLabel("         Rok wydania: ");

    JTextField indexField       = new JTextField(10);
    JTextField nameField        = new JTextField(10);
    JTextField mapTypeField     = new JTextField(10);
    JTextField landField        = new JTextField(10);
    JTextField priceField       = new JTextField(10);
    JTextField releaseYearField = new JTextField(10);

    JButton newButton    = new JButton("Nowa mapa");
    JButton editButton   = new JButton("Zmień dane bieżącej mapy");
    JButton nextButton   = new JButton("Wyświetl następną mapę");
    JButton previousButton   = new JButton("Wyświetl poprzednią mapę");
    JButton saveButton   = new JButton("Zapisz do pliku");
    JButton loadButton   = new JButton("Wczytaj z pliku");
    JButton deleteButton = new JButton("Usuń mapę");
    JButton clearButton  = new JButton("Wyczyść listę");
    JButton infoButton   = new JButton("O programie");
    JButton exitButton   = new JButton("Zakończ aplikację");

    public MapGroupWindowAppOld() {
        setTitle("Lista map");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 420);
        setResizable(false);
        setLocationRelativeTo(null);

        maps.add(toAdd);

        indexLabel.setFont(font);
        nameLabel.setFont(font);
        mapTypeLabel.setFont(font);
        landLabel.setFont(font);
        priceLabel.setFont(font);
        releaseYearLabel.setFont(font);

        indexField.setEditable(false);
        nameField.setEditable(false);
        mapTypeField.setEditable(false);
        landField.setEditable(false);
        priceField.setEditable(false);
        releaseYearField.setEditable(false);

        newButton.addActionListener(this);
        editButton.addActionListener(this);
        nextButton.addActionListener(this);
        previousButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        infoButton.addActionListener(this);
        exitButton.addActionListener(this);

        JPanel panel = new JPanel();

        panel.add(indexLabel);
        panel.add(indexField);

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(mapTypeLabel);
        panel.add(mapTypeField);

        panel.add(landLabel);
        panel.add(landField);

        panel.add(priceLabel);
        panel.add(priceField);

        panel.add(releaseYearLabel);
        panel.add(releaseYearField);

        panel.add(newButton);
        panel.add(editButton);
        panel.add(nextButton);
        panel.add(previousButton);
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(deleteButton);
        panel.add(clearButton);
        panel.add(infoButton);
        panel.add(exitButton);

        setContentPane(panel);

        int mapIndex = 0;
        showMap(mapIndex);

        setVisible(true);

    }

    void showMap(int index){
        if(maps.isMapsEmpty()){
            indexField.setText("");
            nameField.setText("");
            mapTypeField.setText("");
            landField.setText("");
            priceField.setText("");
            releaseYearField.setText("");
        }
        else{
            indexField.setText("" + index);
            nameField.setText(maps.getMap(index).getName());
            mapTypeField.setText("" + maps.getMap(index).getMapType());
            landField.setText(maps.getMap(index).getLand());
            priceField.setText("" + maps.getMap(index).getPrice());
            releaseYearField.setText("" + maps.getMap(index).getReleaseYear());
        }
    }


    private static final String AUTHOR =
            "Języki programowania - Laboratorium 2\n" +
                    "Program: Map - wersja okienkowa\n" +
                    "Autor: Maciej Demucha 259111\n" +
                    "Data: 2 grudnia 2021 r.\n";

    public static void main(String[] args) {
        new MapGroupWindowAppOld();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object eventSource = event.getSource();


        try {
            if (eventSource == newButton) {
                currentMap = MapWindowDialog.createNewMap(this);
                maps.add(currentMap);
                showMap(mapIndex);
                System.out.println("New" + mapIndex);
            }
            if (eventSource == deleteButton) {
               maps.removeMap(mapIndex);
               if(mapIndex >= maps.getSize()){
                   mapIndex--;
               }
               showMap(mapIndex);
                System.out.println("delete" + mapIndex);
            }
            if(eventSource == clearButton){
                maps.clearList();
                mapIndex = 0;
                showMap(mapIndex);
            }
            if(eventSource == nextButton){
                if(maps.isMapsEmpty()){
                    throw new MapException("Żadna mapa nie została utworzona.");
                }
                else{
                    if((mapIndex+1) == maps.getSize()){
                        mapIndex = 0;
                    }
                    else{
                        mapIndex++;
                    }
                    showMap(mapIndex);
                    System.out.println("Next" + mapIndex);
                }
            }
            if(eventSource == previousButton){
                if(maps.isMapsEmpty()){
                    throw new MapException("Żadna mapa nie została utworzona.");
                }
                else{
                    if (mapIndex > 0) {
                        mapIndex--;
                    }
                    showMap(mapIndex);
                    System.out.println("Previous" + mapIndex);
                }
            }
            if (eventSource == saveButton) {
                String fileName = JOptionPane.showInputDialog("Podaj nazwę pliku");
                if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
                Map.printToFile(fileName, maps.getMap(mapIndex));
            }
            if (eventSource == loadButton) {
                String fileName = JOptionPane.showInputDialog("Podaj nazwę pliku");
                if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
                currentMap = Map.readFromFile(fileName);
                maps.add(currentMap);
                showMap(0);
            }
            if (eventSource == editButton) {
                if (maps.isMapsEmpty()) throw new MapException("Żadna mapa nie została utworzona.");
                MapWindowDialog.changeMapData(this, maps.getMap(mapIndex));
                showMap(mapIndex);
                System.out.println("Edit" + mapIndex);
            }
            if (eventSource == infoButton) {
                JOptionPane.showMessageDialog(this, AUTHOR);
            }
            if (eventSource == exitButton) {
                System.exit(0);
            }
        } catch (MapException e) {

            JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}
