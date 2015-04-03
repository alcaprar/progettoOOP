package utils;

import javax.swing.table.DefaultTableModel;

/**
 * Classe per creare tabelle non modificabili dall' utente
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class TableNotEditableModel extends DefaultTableModel{

    /**
     * costruttore della classe
     * richiama il costruttore di DefaultTableModel
     * @param a contenuto della tabella
     * @param b intestazione delle colonne
     */
    public TableNotEditableModel (Object[][] a, Object[] b){
        super(a, b);
    }

    /**
     *sovrascrive il metodo di DefaultTableModel che consente di modificare le celle rendendole non modificabili
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
