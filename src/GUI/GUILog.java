package GUI;

import Manager.TextFileManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUILog extends JFrame {
    private JPanel pannello;
    private JTextArea logTxtArea;
    private JButton eliminaBtn;

    public GUILog(String pathLog) {
        setContentPane(pannello);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setTitle("Log Prestiti/Restituzioni");
        try {
            String log = TextFileManager.readFileAsString(pathLog);
            logTxtArea.setText(log);
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "Impossibile leggere il file di log!");
        }

        eliminaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null,
                        "Sicuro di voler cancellare lo storico?", "Elimazione storico",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    try {
                        TextFileManager.deleteFile(pathLog);
                        logTxtArea.setText("");
                    }catch(IOException ioe){
                        JOptionPane.showMessageDialog(null, "Impossibile cancellare file di log!");
                    }
                }
            }
        });
    }
}
