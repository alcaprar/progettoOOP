package interfacce;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * Created by Giacomo on 12/03/15.
 */
public class Classifica {
    private JPanel panel1;
    private JTable classifica;

    private void createUIComponents() {

        Object[] colonne = {"Pos", "Squadra", "Partite", "V", "N", "P", "DiffReti", "GolF", "GolS", "Punteggio", "Punti"};//bisogna farla vedere
        Object[][] valori = {
                {"1", "Squadra", "Partite", "V", "N", "P", "DiffReti", "GolF", "GolS", "Punteggio", "Punti"},
                {"2", "Squadra", "Partite", "V", "N", "P", "DiffReti", "GolF", "GolS", "Punteggio", "Punti"}//i campi così sono modificabili non devono
        };
        DefaultTableModel classificamodel = new DefaultTableModel(valori, colonne);
        classifica = new JTable();
        classifica.setModel(classificamodel);
    }

}


