package lab1.ui.gui;

import lab1.data.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import static java.awt.Dialog.ModalityType.DOCUMENT_MODAL;

public class MapGroupWindowDialog extends JDialog implements ActionListener {
    private MapGroup currentGroup;
    private Map currentMap;

    private static final long serialVersionUID = 1L;

    private static final String GREETING_MESSAGE =
            "Program do zarządzania grupą map " +
                    "- wersja okienkowa\n\n" +
                    "Autor: Maciej Demucha\n" +
                    "Data:  9 grudnia 2021 r.\n";

    //Pola z nazwami grupy i kolekcji
    JLabel nameLabel = new JLabel("      Nazwa grupy: ");
    JLabel collectionLabel  = new JLabel("  Rodzaj kolekcji: ");

    JTextField nameField = new JTextField(30);
    JTextField collectionField    = new JTextField(30);

    ViewMapList viewMapList;

    //Pasek menu na górze
    JMenuBar menuBar            = new JMenuBar();

    JMenu menuList              = new JMenu("Lista map");
    JMenu menuSort              = new JMenu("Sortowanie");
    JMenu menuProperties        = new JMenu("Właściwości");
    JMenu menuAbout             = new JMenu("O programie");

    JMenuItem menuNewPerson         = new JMenuItem("Dodaj nową mapę");
    JMenuItem menuEditPerson        = new JMenuItem("Edytuj mapę");
    JMenuItem menuDeletePerson      = new JMenuItem("Usuń mapę");
    JMenuItem menuLoadPerson        = new JMenuItem("Wczytaj mapę z pliku");
    JMenuItem menuSavePerson        = new JMenuItem("Zapisz mapę do pliku");

    JMenuItem menuSortAl         = new JMenuItem("alfabetycznie");
    JMenuItem menuSortYear         = new JMenuItem("wg. roku wydania");
    JMenuItem menuSortType         = new JMenuItem("wg. rodzaju");

    JMenuItem menuChangeName         = new JMenuItem("Zmień nazwę");
    JMenuItem menuChangeCollection   = new JMenuItem("Zmień typ kolekcji");

    JMenuItem menuAuthor             = new JMenuItem("O programie");

    // Przyciski wyświetlane na panelu w głównym oknie aplikacji
    JButton buttonNewPerson      = new JButton("Dodaj nową mapę");
    JButton buttonEditPerson     = new JButton("Edytuj mapę");
    JButton buttonDeletePerson   = new JButton(" Usuń mapę");
    JButton buttonLoadPerson     = new JButton("Wczytaj mapę z pliku");
    JButton buttonSavePerson     = new JButton("Zapisz mapę do pliku");



    public MapGroupWindowDialog(Window parent, MapGroup currentGroup) {

        super(parent, DOCUMENT_MODAL);
        this.currentGroup = currentGroup;

        setTitle("Modyfikacja grupy map");
        setSize(450, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        nameField.setEditable(false);
        collectionField.setEditable(false);

        nameField.setText(currentGroup.getName());
        collectionField.setText(currentGroup.getType().toString());

        setJMenuBar(menuBar);
        menuBar.add(menuList);
        menuBar.add(menuSort);
        menuBar.add(menuProperties);
        menuBar.add(menuAbout);

        menuList.add(menuNewPerson);
        menuList.add(menuEditPerson);
        menuList.add(menuDeletePerson);
        menuList.add(menuLoadPerson);
        menuList.add(menuSavePerson);

        menuSort.add(menuSortAl);
        menuSort.add(menuSortYear);
        menuSort.add(menuSortType);

        menuProperties.add(menuChangeName);
        menuProperties.add(menuChangeCollection);

        menuAbout.add(menuAuthor);

        buttonNewPerson.addActionListener(this);
        buttonEditPerson.addActionListener(this);
        buttonDeletePerson.addActionListener(this);
        buttonLoadPerson.addActionListener(this);
        buttonSavePerson.addActionListener(this);

        menuNewPerson.addActionListener(this);
        menuEditPerson.addActionListener(this);
        menuDeletePerson.addActionListener(this);
        menuLoadPerson.addActionListener(this);
        menuSavePerson.addActionListener(this);
        menuSortAl.addActionListener(this);
        menuSortYear.addActionListener(this);
        menuSortType.addActionListener(this);
        menuChangeName.addActionListener(this);
        menuChangeCollection.addActionListener(this);
        menuAuthor.addActionListener(this);

        JPanel panel = new JPanel();
        viewMapList = new ViewMapList(currentGroup, 400 ,250);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(collectionLabel);
        panel.add(collectionField);
        panel.add(viewMapList);
        panel.add(buttonNewPerson);
        panel.add(buttonEditPerson);
        panel.add(buttonDeletePerson);
        panel.add(buttonLoadPerson);
        panel.add(buttonSavePerson);


        setContentPane(panel);

        viewMapList.refreshView();
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent event) {
        Object eventSource = event.getSource();
        try {
            if (eventSource == buttonNewPerson || eventSource == menuNewPerson) {
                currentMap = MapWindowDialog.createNewMap(this);
                currentGroup.add(currentMap);
            }

            if (eventSource == buttonEditPerson || eventSource == menuEditPerson) {
                int index = viewMapList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Map> iterator = currentGroup.iterator();
                    while (index-- > 0)
                        iterator.next();
                    MapWindowDialog.changeMapData(this, iterator.next());
                }
            }

            if (eventSource == buttonDeletePerson || eventSource == menuDeletePerson) {
                int index = viewMapList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Map> iterator = currentGroup.iterator();
                    while (index-- > 0)
                        iterator.next();
                    iterator.remove();
                }
            }

            if (eventSource == buttonSavePerson || eventSource == menuSavePerson) {
                int index = viewMapList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Map> iterator = currentGroup.iterator();
                    while (index-- > 0)
                        iterator.next();
                    Map map = iterator.next();

                    JFileChooser chooser = new JFileChooser(".");
                    int returnVal = chooser.showSaveDialog(this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        Map.printToFile(chooser.getSelectedFile().getName(), map);
                    }
                }
            }

            if (eventSource == buttonLoadPerson || eventSource == menuLoadPerson) {
                JFileChooser chooser = new JFileChooser(".");
                int returnVal = chooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    Map map = Map.readFromFile(chooser.getSelectedFile().getName());
                    currentGroup.add(map);
                }
            }

            if(eventSource == menuSortAl){
                currentGroup.sortName();
            }

            if(eventSource == menuSortYear){
                currentGroup.sortReleaseYear();
            }

            if(eventSource == menuSortType){
                currentGroup.sortType();
            }

            if(eventSource == menuChangeName){
                String groupName = JOptionPane.showInputDialog("Podaj nazwę grupy");
                currentGroup.setName(groupName);

                nameField.setText(groupName);
            }

            if(eventSource == menuChangeCollection){
                GroupType [] collectionslist = GroupType.values();

                GroupType type = (GroupType) JOptionPane.showInputDialog(null,
                        "Zmień typ kolekcji:",
                        "Zmień typ kolekcji", JOptionPane.QUESTION_MESSAGE,
                        null, collectionslist, collectionslist[0]);

                currentGroup.setType(type);
                collectionField.setText(type.toString());
            }

            if (eventSource == menuAuthor) {
                JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
            }
        }
        catch (MapException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }

        // Aktualizacja zawartości tabeli z grupa.
        viewMapList.refreshView();

    }


    public static MapGroup createNewGroupOfPeople(Window parent) throws MapException {
        String groupName = JOptionPane.showInputDialog("Podaj nazwę grupy");
        if (groupName == null || groupName.equals("")) return null;

        GroupType [] collectionslist = GroupType.values();
        GroupType type = (GroupType) JOptionPane.showInputDialog(null,
                "Zmień typ kolekcji:",
                "Zmień typ kolekcji", JOptionPane.QUESTION_MESSAGE,
                null, collectionslist, collectionslist[0]);

        MapGroup currentGroup = new MapGroup(type, groupName);

        MapGroupWindowDialog dialog = new MapGroupWindowDialog(parent, currentGroup);
        return dialog.currentGroup;
    }
}

/*
 * Pomocnicza klasa do wyświetlania listy grup
 * w postaci tabeli na panelu okna głównego
 */
class ViewMapList extends JScrollPane {
    private static final long serialVersionUID = 1L;

    private MapGroup list;
    private JTable table;
    private DefaultTableModel tableModel;

    public ViewMapList(MapGroup list, int width, int height){
        this.list = list;
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createTitledBorder("Lista osób:"));

        String[] tableHeader = { "Nazwa", "Rodzaj", "Obszar","Cena", "Rok wydania" };
        tableModel = new DefaultTableModel(tableHeader, 0);
        table = new JTable(tableModel) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; // Blokada możliwości edycji komórek tabeli
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        setViewportView(table);
    }

    void refreshView(){
        tableModel.setRowCount(0);
        for (Map map : list) {
            if (map != null) {
                String[] row = { map.getName(), map.getMapType().toString(), map.getLand(), "" + map.getPrice(), "" + map.getReleaseYear() };
                tableModel.addRow(row);
            }
        }
    }

    int getSelectedIndex(){
        int index = table.getSelectedRow();
        if (index<0) {
            JOptionPane.showMessageDialog(this, "Żadana osoba nie jest zaznaczona.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        return index;
    }

}