package utils;

import javax.swing.table.DefaultTableModel;

/**
 * Created by Giacomo on 26/03/15.
 */
public class TableNotEditableModel extends DefaultTableModel{

    //costruttore che richiama il costruttore di DefaultTableModel passandogli le righe e le intestazione delle colonne
    public TableNotEditableModel (Object[][] a, Object[] b){
        super(a, b);
    }
    //sovrascrive il metodo che consente di modificare le celle rendendole non modificabili
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
