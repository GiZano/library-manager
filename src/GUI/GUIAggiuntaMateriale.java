package GUI;

import Manager.MaterialManager;
import Oggetti.Dvd;
import Oggetti.Libro;
import Oggetti.MaterialeBibliotecario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class GUIAggiuntaMateriale extends JFrame {
    private JPanel pannello;
    private JPanel pannelloDati;
    private JPanel pannelloButton;
    private JComboBox<String> tipoCombo;
    private JTextField idTxt;
    private JTextField extra1Txt;
    private JTextField titoloTxt;
    private JTextField extra2Txt;
    private JLabel idLbl;
    private JLabel titleLbl;
    private JLabel extra1Lbl;
    private JLabel extra2Lbl;
    private JButton chiudiBtn;
    private JButton aggiungiBtn;

    ArrayList<MaterialeBibliotecario> materiale;

    public GUIAggiuntaMateriale(ArrayList<MaterialeBibliotecario> materiale, GUIPrincipale guiPadre) {

        this.materiale = materiale;

        setContentPane(pannello);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setTitle("Aggiunta Materiale");
        setVisible(true);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        extra1Lbl.setText("Autore");
        extra2Lbl.setText("ISBN");


        chiudiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        tipoCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.requireNonNull(tipoCombo.getSelectedItem()).toString().equalsIgnoreCase("Libro")) {
                    extra1Lbl.setText("Autore");
                    extra2Lbl.setText("ISBN");
                }
                else if(Objects.requireNonNull(tipoCombo.getSelectedItem()).toString().equalsIgnoreCase("DVD")) {
                    extra1Lbl.setText("Regista");
                    extra2Lbl.setText("Durata in minuti");
                }
            }
        });

        aggiungiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] datiNuovoMateriale = new String[4];
                datiNuovoMateriale[0] = idTxt.getText();
                datiNuovoMateriale[1] = titoloTxt.getText();
                datiNuovoMateriale[2] = extra1Txt.getText();
                datiNuovoMateriale[3] = extra2Txt.getText();

                String[] datiPerTabella = new String[3];
                datiPerTabella[0] = idTxt.getText();
                datiPerTabella[1] = titoloTxt.getText();
                datiPerTabella[2] = "Si";


                switch(Objects.requireNonNull(tipoCombo.getSelectedItem()).toString()){
                    case "Libro":
                        Libro nuovoLibro = new Libro(datiNuovoMateriale[0], datiNuovoMateriale[1], true, datiNuovoMateriale[2], datiNuovoMateriale[3]);
                        if((MaterialManager.findLibro(nuovoLibro, MaterialManager.dividiLibriDaDvd(materiale))) == -1){
                            if(MaterialManager.findMateriale(nuovoLibro, materiale) == -1){

                                materiale.add(nuovoLibro);
                                guiPadre.caricaInTabella(datiPerTabella);
                                JOptionPane.showMessageDialog(null, "Materiale aggiunto con successo!");
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Materiale con ID \""+datiNuovoMateriale[0]+"\" già esistente!");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Libro con ISBN \""+datiNuovoMateriale[3]+"\" già esistente!");
                        }
                        break;
                    case "Dvd":
                        try {
                            Dvd nuovoDvd = new Dvd(datiNuovoMateriale[0], datiNuovoMateriale[1], true, datiNuovoMateriale[2], Integer.parseInt(datiNuovoMateriale[3]));
                            if(MaterialManager.findMateriale(nuovoDvd, materiale) == -1){
                                materiale.add(nuovoDvd);
                                guiPadre.caricaInTabella(datiPerTabella);
                                JOptionPane.showMessageDialog(null, "Materiale aggiunto con successo!");
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Materiale con ID \""+datiNuovoMateriale[0]+"\" già esistente!");
                            }

                        }catch(NumberFormatException nfe){
                            JOptionPane.showMessageDialog(null, "Inserire valori numerici all'interno del campo \"Durata in minuti\" !!");
                        }
                        break;
                    default:
                }

            }
        });

    }

    private void createUIComponents() {
        String[] tipiMateriale = {"Libro", "Dvd"};
        tipoCombo = new JComboBox<String>(tipiMateriale);
    }
}
