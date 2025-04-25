package GUI;

import Manager.*;
import Oggetti.Dvd;
import Oggetti.Libro;
import Oggetti.MaterialeBibliotecario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GUIPrincipale extends JFrame {
    private JPanel pannello;
    private JTable materialiTable;
    private JButton prendiBtn;
    private JButton restituisciBtn;
    private JScrollPane scrollPaneTable;
    private JButton aggiungiBtn;
    private JButton logBtn;

    private ArrayList<MaterialeBibliotecario> catalogo;
    private final String[] header = {"ID", "Titolo", "Disponibile"};
    private final String pathDati = "data/catalogo.txt";
    private final String pathLog = "data/log.log";

    public GUIPrincipale() {

        setContentPane(pannello);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
        setVisible(true);
        setTitle("Inventario Materiale");
        setSize(800, 600);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                LocalDateTime now = LocalDateTime.now();
                StringBuilder log = new StringBuilder();
                log.append(now).append("\n");
                if (JOptionPane.showConfirmDialog(null,
                        "Vuoi salvare i dati prima di uscire?", "Salvare?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    try {
                        MaterialManager.salvaDati(pathDati, catalogo);
                        log.append("Uscita con salvataggio");
                    }catch(IOException ioe){
                        JOptionPane.showMessageDialog(null, "Impossibile caricare dati!");
                        log.append("Uscita senza salvataggio");
                    }
                }
                else{
                    log.append("Uscita senza salvataggio!");
                }
                try {
                    log.append("\n\n");
                    TextFileManager.appendStringToFile(pathLog, log.toString());
                }catch(IOException ioe){
                    System.out.println("Impossibile salvare il log!");
                }
                System.exit(0);
            }
        });

        aggiungiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generaGUIAggiunta();
            }
        });

        prendiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idPrestito = JOptionPane.showInputDialog(null, "Inserire ID del materiale da prendere in prestito", "Prestito", JOptionPane.QUESTION_MESSAGE);
                int index = MaterialManager.findMateriale(idPrestito, catalogo);
                if(index != -1){
                    if(catalogo.get(index).getDisponibile()){
                        catalogo.get(index).setDisponibile(false);
                        ricaricaTabella();
                        JOptionPane.showMessageDialog(null, "Materiale preso in prestito!");
                        StringBuilder log = new StringBuilder();
                        LocalDateTime now = LocalDateTime.now();
                        log.append(now).append("\n").append("Prestito ").append(catalogo.get(index) instanceof Libro ? "Libro" : "Dvd").append(" id: ").append(catalogo.get(index).getId()).append("\n\n");
                        try {
                            TextFileManager.appendStringToFile(pathLog, log.toString());
                        }catch(IOException ioe){
                            JOptionPane.showMessageDialog(null, "Impossibile salvare nello storico!");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Materiale già in prestito!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Materiale con ID \"" + idPrestito + "\" non esistente!" );
                }
            }
        });
        logBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUILog guiLog = new GUILog(pathLog);
            }
        });
        restituisciBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idRestituzione = JOptionPane.showInputDialog(null, "Inserire ID del materiale da restituire", "Restituzione", JOptionPane.QUESTION_MESSAGE);
                int index = MaterialManager.findMateriale(idRestituzione, catalogo);
                if(index != -1){
                    if(!catalogo.get(index).getDisponibile()){
                        catalogo.get(index).setDisponibile(true);
                        ricaricaTabella();
                        JOptionPane.showMessageDialog(null, "Materiale restituito!");
                        StringBuilder log = new StringBuilder();
                        LocalDateTime now = LocalDateTime.now();
                        log.append(now).append("\n").append("Restituzione ").append(catalogo.get(index) instanceof Libro ? "Libro" : "Dvd").append(" id: ").append(catalogo.get(index).getId()).append("\n\n");
                        try {
                            TextFileManager.appendStringToFile(pathLog, log.toString());
                        }catch(IOException ioe){
                            JOptionPane.showMessageDialog(null, "Impossibile salvare nello storico!");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Materiale già restituito!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Materiale con ID \"" + idRestituzione + "\" non esistente!" );
                }
            }
        });
    }

    private void createUIComponents() {

        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        materialiTable = new JTable(model);
        model.setColumnIdentifiers(header);
        scrollPaneTable = new JScrollPane(materialiTable);
        this.catalogo = new ArrayList<MaterialeBibliotecario>();

        try {
            String datiCaricati = TextFileManager.readFileAsString(pathDati);
            if(datiCaricati.isEmpty()){
                JOptionPane.showMessageDialog(null, "Nessun dato in memoria!");
            }
            else{
                String[] materiali = datiCaricati.split("\n");
                for(String materiale : materiali){
                    String[] datiMateriale = materiale.split(",");
                    String[] datiTabella = new String[3];
                    datiTabella[0] = datiMateriale[1];
                    datiTabella[1] = datiMateriale[2];
                    datiTabella[2] = (datiMateriale[3].equals("true") ? "Si" : "No");
                    MaterialeBibliotecario materialeCaricato;
                    switch(datiMateriale[0]){
                        case "Libro":
                            materialeCaricato = new Libro(datiMateriale[1], datiMateriale[2], (datiMateriale[3].equals("true")), datiMateriale[4], datiMateriale[5]);
                            this.catalogo.add(materialeCaricato);
                            caricaInTabella(datiTabella);
                            break;
                        case "Dvd":
                            materialeCaricato = new Dvd(datiMateriale[1], datiMateriale[2], (datiMateriale[3].equals("true")), datiMateriale[4], Integer.parseInt(datiMateriale[5]));
                            this.catalogo.add(materialeCaricato);
                            caricaInTabella(datiTabella);
                            break;
                        default:
                    }

                }
            }
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "Impossibile caricare i dati!");
        }
    }

    /**
     * Caricamento in tabella di una riga specificata nei parametri
     * @param materiale riga da aggiungere alla tabella
     */
    public void caricaInTabella(String[] materiale){
        DefaultTableModel tableModel = (DefaultTableModel) materialiTable.getModel();
        tableModel.addRow(materiale);
    }

    /**
     * Ricaricamento dei dati in tabella presi dal catalogo in memoria
     */
    private void ricaricaTabella(){
        DefaultTableModel tableModel = (DefaultTableModel) materialiTable.getModel();
        tableModel.setRowCount(0);
        for(MaterialeBibliotecario materiale : this.catalogo){
            String[] datiTabella = new String[3];
            datiTabella[0] = materiale.getId();
            datiTabella[1] = materiale.getTitolo();
            datiTabella[2] = (materiale.getDisponibile() ? "Si" : "No");
            tableModel.addRow(datiTabella);
        }
    }

    /**
     * Apertura GUI per l'aggiunta di nuovo materiale
     */
    private void generaGUIAggiunta() {
        GUIAggiuntaMateriale aggiuntaMateriale = new GUIAggiuntaMateriale(catalogo, this);
    }

}
