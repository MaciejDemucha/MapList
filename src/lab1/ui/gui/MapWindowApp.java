package lab1.ui.gui;

import lab1.data.MapList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapWindowApp extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    MapList maps = new MapList();

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
    JButton nextButton   = new JButton("Wyświetl następną mapę");
    JButton saveButton   = new JButton("Zapisz do pliku");
    JButton loadButton   = new JButton("Wczytaj z pliku");
    JButton deleteButton = new JButton("Usuń mapę");
    JButton clearButton = new JButton("Wyczyść listę");
    JButton infoButton   = new JButton("O programie");
    JButton exitButton   = new JButton("Zakończ aplikację");

    public MapWindowApp() {
        setTitle("Lista map");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 400);
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
        nextButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
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
        panel.add(nextButton);
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(deleteButton);
        panel.add(clearButton);
        panel.add(infoButton);
        panel.add(exitButton);

        setContentPane(panel);

        showMap(0);

        setVisible(true);

    }

    void showMap(int index){
        if(maps.isMapsEmpty()){
            nameField.setText("");
            mapTypeField.setText("");
            landField.setText("");
            priceField.setText("");
            releaseYearField.setText("");
        }
        else{
            nameField.setText(maps.getMap(index).getName());
            mapTypeField.setText("" + maps.getMap(index).getMapType());
            landField.setText(maps.getMap(index).getLand());
            priceField.setText("" + maps.getMap(index).getPrice());
            releaseYearField.setText("" + maps.getMap(index).getReleaseYear());
        }
    }

    private static final String AUTHOR =
            "Języki programowania - Laboratorium 1\n" +
                    "Program: Map - wersja okienkowa\n" +
                    "Autor: Maciej Demucha 259111\n" +
                    "Data: 28 październik 2021 r.\n";

    public static void main(String[] args) {
        new MapWindowApp();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
