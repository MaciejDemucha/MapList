/*
 * Program: Program testujący operacje na obiektach klasy Map reprezentujących różne rodzaje map
 *
 *       Plik: MapWindowApp.java
 *
 *          Aplikacja okienkowa pozwalająca przetestować poszczególne operacje na obiekcie klasy Map
 *          WERSJA DLA 1 MAPY
 *
 *   Autor: Maciej Demucha 259111
 *    Data: 29 listopad 2021 r.
 *
 */

package lab1.ui.gui;

import lab1.data.Map;
import lab1.data.MapException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MapWindowApp extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    //Zmienna przechowująca obecnie zapisaną mapę
    private Map currentMap;

    //Czcionka
    Font font = new Font("MonoSpaced", Font.BOLD, 12);
    //Podpisy pól
    JLabel nameLabel        = new JLabel("               Nazwa: ");
    JLabel mapTypeLabel     = new JLabel("         Rodzaj mapy: ");
    JLabel landLabel        = new JLabel("Przedstawiony obszar: ");
    JLabel priceLabel       = new JLabel("                Cena: ");
    JLabel releaseYearLabel = new JLabel("         Rok wydania: ");
    //Pola wyświetlające dane mapy
    JTextField nameField        = new JTextField(10);
    JTextField mapTypeField     = new JTextField(10);
    JTextField landField        = new JTextField(10);
    JTextField priceField       = new JTextField(10);
    JTextField releaseYearField = new JTextField(10);
    //przyciski
    JButton newButton    = new JButton("Nowa mapa");
    JButton editButton   = new JButton("Zmień dane bieżącej mapy");
    JButton saveButton   = new JButton("Zapisz do pliku");
    JButton loadButton   = new JButton("Wczytaj z pliku");
    JButton deleteButton = new JButton("Usuń mapę");
    JButton infoButton   = new JButton("O programie");
    JButton exitButton   = new JButton("Zakończ aplikację");

    JMenuBar bar = new JMenuBar(); //pasek menu
    //przyciski w pasku
    JMenu file = new JMenu("Plik");

    //przyciski w rozsuwanych elementach paska menu
    JMenuItem info = new JMenuItem("O programie");
    JMenuItem newMap = new JMenuItem("Nowa mapa");
    JMenuItem editMap = new JMenuItem("Edytuj mapę");
    JMenuItem deleteMap = new JMenuItem("Usuń mapę");

    JMenuItem load = new JMenuItem("Wczytaj mapę");
    JMenuItem save = new JMenuItem("Zapisz mapę");
    //konstruktor aplikacji okienkowej
    public MapWindowApp() {
        setTitle("Mapa");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setJMenuBar(bar); //Dodanie paska menu
        //dodanie przycisków do paska menu
        bar.add(file);

        //dodanie opcji do przycisku "plik"
        file.add(newMap);
        file.add(editMap);
        file.add(deleteMap);
        file.add(load);
        file.add(save);
        file.add(info);
        //ustawienie czcionki
        nameLabel.setFont(font);
        mapTypeLabel.setFont(font);
        landLabel.setFont(font);
        priceLabel.setFont(font);
        releaseYearLabel.setFont(font);
        //Brak możliwości edycji pól
        nameField.setEditable(false);
        mapTypeField.setEditable(false);
        landField.setEditable(false);
        priceField.setEditable(false);
        releaseYearField.setEditable(false);
        //Dodanie ActionListenerów
        newButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        deleteButton.addActionListener(this);
        infoButton.addActionListener(this);
        exitButton.addActionListener(this);

        newMap.addActionListener(this);
        editMap.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
        deleteMap.addActionListener(this);
        info.addActionListener(this);
        //panel
        JPanel panel = new JPanel();
        //Dodanie komponentów do panelu
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
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(deleteButton);
        panel.add(infoButton);
        panel.add(exitButton);
        //Wstawienie panelu
        setContentPane(panel);
        //Wyświetlenie mapy
        showCurrentMap();
        //Ustawienie widoczności okienka
        setVisible(true);

    }

    //Wyświetlenie mapy
    void showCurrentMap(){
        if(currentMap == null){
            nameField.setText("");
            mapTypeField.setText("");
            landField.setText("");
            priceField.setText("");
            releaseYearField.setText("");
        }
        else{
            nameField.setText(currentMap.getName());
            mapTypeField.setText("" + currentMap.getMapType());
            landField.setText(currentMap.getLand());
            priceField.setText("" + currentMap.getPrice());
            releaseYearField.setText("" + currentMap.getReleaseYear());
        }
    }
    //Opis aplikacji
    private static final String AUTHOR =
            "Języki programowania - Laboratorium 2\n" +
                    "Program: Map - wersja okienkowa\n" +
                    "Autor: Maciej Demucha 259111\n" +
                    "Data: 2 grudnia 2021 r.\n";

    public static void main(String[] args) {
        new MapWindowApp();
    }
    //Wydarzenia po naciśnięciu przycisków
    @Override
    public void actionPerformed(ActionEvent event) {
        Object eventSource = event.getSource();


        try {
            if (eventSource == newButton || eventSource == newMap) {
                currentMap = MapWindowDialog.createNewMap(this);
                showCurrentMap();
            }

            if (eventSource == deleteButton || eventSource == deleteMap) {
                currentMap = null;
                showCurrentMap();
            }

            if (eventSource == saveButton || eventSource == save) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                int response = fileChooser.showSaveDialog(this);

                if(response == JFileChooser.APPROVE_OPTION){
                    String fileName = fileChooser.getSelectedFile().getName();
                    Map.printToFile(fileName, currentMap);
                }

            }

            if (eventSource == loadButton || eventSource == load) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                int response = fileChooser.showOpenDialog(this);

                if(response == JFileChooser.APPROVE_OPTION){
                    String fileName = fileChooser.getSelectedFile().getName();
                    currentMap = Map.readFromFile(fileName);
                    showCurrentMap();
                }

            }
            if (eventSource == editButton || eventSource == editMap) {
                if (currentMap == null) throw new MapException("Żadna mapa nie została utworzona.");
                MapWindowDialog.changeMapData(this, currentMap);
                showCurrentMap();
            }
            if (eventSource == infoButton || eventSource == info) {
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
