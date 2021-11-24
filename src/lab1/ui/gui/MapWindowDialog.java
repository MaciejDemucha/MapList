package lab1.ui.gui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lab1.data.Map;
import lab1.data.MapException;
import lab1.data.MapType;


public class MapWindowDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private Map map;

    Font font = new Font("MonoSpaced", Font.BOLD, 12);

    // Etykiety wyświetlane na panelu
    JLabel nameLabel        = new JLabel("               Nazwa: ");
    JLabel mapTypeLabel     = new JLabel("         Rodzaj mapy: ");
    JLabel landLabel        = new JLabel("Przedstawiony obszar: ");
    JLabel priceLabel       = new JLabel("                Cena: ");
    JLabel releaseYearLabel = new JLabel("         Rok wydania: ");

    // Pola tekstowe wyświetlane na panelu
    JTextField nameField        = new JTextField(10);
    JTextField landField        = new JTextField(10);
    JTextField priceField       = new JTextField(10);
    JTextField releaseYearField = new JTextField(10);
    JComboBox<MapType> typeBox = new JComboBox<MapType>(MapType.values());

    // Przyciski wyświetlane na panelu
    JButton OKButton = new JButton("  OK  ");
    JButton CancelButton = new JButton("Anuluj");


   
    private MapWindowDialog(Window parent, Map map) {

        super(parent, Dialog.ModalityType.DOCUMENT_MODAL);

        // Konfiguracja parametrów tworzonego okna dialogowego
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(260, 230);
        setResizable(false);
        setLocationRelativeTo(parent);

        // zapamiętanie referencji do mapy, której dane będą modyfikowane.
        this.map = map;

        // Ustawienie tytułu okna oraz wypełnienie zawartości pól tekstowych
        if (map==null){
            setTitle("Nowa mapa");
        } else{
            setTitle(map.toString());
            nameField.setText(map.getName());
            landField.setText(map.getLand());
            priceField.setText(""+map.getPrice());
            releaseYearField.setText(""+map.getReleaseYear());
            typeBox.setSelectedItem(map.getMapType());
        }

        // Dodanie słuchaczy zdarzeń do przycisków.
        // UWAGA: słuchaczem zdarzeń będzie metoda actionPerformed
        //        zaimplementowana w tej klasie i wywołana dla
        //        bieżącej instancji okna dialogowego - referencja this
        OKButton.addActionListener( this );
        CancelButton.addActionListener( this );

        // Utworzenie głównego panelu okna dialogowego.
        // Domyślnym menedżerem rozdładu dla panelu będzie
        // FlowLayout, który układa wszystkie komponenty jeden za drugim.
        JPanel panel = new JPanel();

        // Dodanie i rozmieszczenie na panelu wszystkich komponentów GUI.
        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(mapTypeLabel);
        panel.add(typeBox);

        panel.add(landLabel);
        panel.add(landField);

        panel.add(priceLabel);
        panel.add(priceField);

        panel.add(releaseYearLabel);
        panel.add(releaseYearField);

        panel.add(OKButton);
        panel.add(CancelButton);

        // Umieszczenie Panelu w oknie dialogowym.
        setContentPane(panel);


        setVisible(true);
    }


    /*
     * Implementacja interfejsu ActionListener.
     *
     * Metoda actionPerformrd bedzie automatycznie wywoływana
     * do obsługi wszystkich zdarzeń od obiektów, którym jako słuchacza zdarzeń
     * dołączono obiekt reprezentujący bieżącą instancję okna aplikacji (referencja this)
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        // Odczytanie referencji do obiektu, który wygenerował zdarzenie.
        Object source = event.getSource();

        if (source == OKButton) {
            try {
                if (map == null) {
                    map = new Map(" ", MapType.UNKNOWN, " ", 0, 0);
                    map.setName(nameField.getText());
                    map.setLand(landField.getText());
                } else {
                    map.setName(nameField.getText());
                    map.setLand(landField.getText());
                }

                map.setPrice(priceField.getText());
                map.setReleaseYear(releaseYearField.getText());
                map.setMapType((MapType) typeBox.getSelectedItem());

                // Zamknięcie okna i zwolnienie wszystkich zasobów.
                dispose();
            } catch (MapException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (source == CancelButton) {
            dispose();
        }
    }



    public static Map createNewMap(Window parent) {
        MapWindowDialog dialog = new MapWindowDialog(parent, null);
        return dialog.map;
    }


    public static void changeMapData(Window parent, Map map) {
        new MapWindowDialog(parent, map);
    }

}

