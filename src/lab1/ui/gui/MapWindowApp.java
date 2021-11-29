/*
 * Program: Program testujący operacje na obiektach klasy Map reprezentujących różne rodzaje map
 *
 *       Plik: MapWindowApp.java
 *
 *          Aplikacja okienkowa pozwalająca przetestować poszczególne operacje na obiekcie klasy Map
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

public class MapWindowApp extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private Map currentMap;


    Font font = new Font("MonoSpaced", Font.BOLD, 12);

    JLabel nameLabel        = new JLabel("               Nazwa: ");
    JLabel mapTypeLabel     = new JLabel("         Rodzaj mapy: ");
    JLabel landLabel        = new JLabel("Przedstawiony obszar: ");
    JLabel priceLabel       = new JLabel("                Cena: ");
    JLabel releaseYearLabel = new JLabel("         Rok wydania: ");

    JTextField nameField        = new JTextField(10);
    JTextField mapTypeField     = new JTextField(10);
    JTextField landField        = new JTextField(10);
    JTextField priceField       = new JTextField(10);
    JTextField releaseYearField = new JTextField(10);

    JButton newButton    = new JButton("Nowa mapa");
    JButton editButton   = new JButton("Zmień dane bieżącej mapy");
    JButton saveButton   = new JButton("Zapisz do pliku");
    JButton loadButton   = new JButton("Wczytaj z pliku");
    JButton deleteButton = new JButton("Usuń mapę");
    JButton infoButton   = new JButton("O programie");
    JButton exitButton   = new JButton("Zakończ aplikację");

    public MapWindowApp() {
        setTitle("Mapa");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 420);
        setResizable(false);
        setLocationRelativeTo(null);

        nameLabel.setFont(font);
        mapTypeLabel.setFont(font);
        landLabel.setFont(font);
        priceLabel.setFont(font);
        releaseYearLabel.setFont(font);

        nameField.setEditable(false);
        mapTypeField.setEditable(false);
        landField.setEditable(false);
        priceField.setEditable(false);
        releaseYearField.setEditable(false);

        newButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        deleteButton.addActionListener(this);
        infoButton.addActionListener(this);
        exitButton.addActionListener(this);

        JPanel panel = new JPanel();

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

        setContentPane(panel);

       showCurrentMap();

        setVisible(true);

    }


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

    private static final String AUTHOR =
            "Języki programowania - Laboratorium 2\n" +
                    "Program: Map - wersja okienkowa\n" +
                    "Autor: Maciej Demucha 259111\n" +
                    "Data: 2 grudnia 2021 r.\n";

    public static void main(String[] args) {
        new MapWindowApp();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object eventSource = event.getSource();


        try {
            if (eventSource == newButton) {
                currentMap = MapWindowDialog.createNewMap(this);
                showCurrentMap();
            }
            if (eventSource == deleteButton) {
                currentMap = null;
                showCurrentMap();
            }

            if (eventSource == saveButton) {
                String fileName = JOptionPane.showInputDialog("Podaj nazwę pliku");
                if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
                Map.printToFile(fileName, currentMap);
            }
            if (eventSource == loadButton) {
                String fileName = JOptionPane.showInputDialog("Podaj nazwę pliku");
                if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
                currentMap = Map.readFromFile(fileName);
                showCurrentMap();
            }
            if (eventSource == editButton) {
                if (currentMap == null) throw new MapException("Żadna mapa nie została utworzona.");
                MapWindowDialog.changeMapData(this, currentMap);
                showCurrentMap();
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
