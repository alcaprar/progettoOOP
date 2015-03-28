import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import interfacce.*;


/**
 * Gestore fantacalcio.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Fantacalcio{
    public static void main(String args[]){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            //eccezione ignorata
        }
        catch (ClassNotFoundException e) {
            //eccezzione ignorata
        }
        catch (InstantiationException e) {
            //eccezzione ignorata
        }
        catch (IllegalAccessException e) {
            //eccezione ignorata
        }

        Login logingui = new Login();
  	}
}
