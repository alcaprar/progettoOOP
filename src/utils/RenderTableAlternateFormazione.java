package utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Classe per colorare le tabelle delle formazioni, i titolari di un colore le riserve di un altro
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class RenderTableAlternateFormazione extends DefaultTableCellRenderer {
    /**
     Sovrascrive il metodo della classe DefaultTableCellRenderer colorando le prime 11 righe di un colore e le restanti di un altro
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setBackground(row < 11  ? Color.WHITE : new Color(136, 213, 215));
        return c;
    }
}
