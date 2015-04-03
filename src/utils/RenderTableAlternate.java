package utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Classe per colorare le tabelle a righe alternate
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class RenderTableAlternate extends DefaultTableCellRenderer {
    /**
     Sovrascrive il metodo della classe DefaultTableCellRenderer colorando le righe pari di un colore e le dispari di un altro
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setBackground(row % 2 == 0 ? new Color(163, 224, 210) : new Color(136, 213, 215));
        return c;
    }
}
