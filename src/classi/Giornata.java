package classi;

import java.util.ArrayList;

/**
 * Created by Giacomo on 18/03/15.
 */
public class Giornata {
    private int numGiornata;
    private GiornataReale GioReale;
    //ArrayList partite

    public Giornata(int numGiornata, GiornataReale GioReale) {
        this.numGiornata = numGiornata;
        this.GioReale = GioReale;
    }

    public void calcolaGiornata() {

    }

    public int getNumGiornata() {
        return numGiornata;
    }

    public void setNumGiornata(int numGiornata) {
        this.numGiornata = numGiornata;
    }

    public GiornataReale getNumGioReale() {
        return GioReale;
    }

    public void setNumGioReale(GiornataReale numGioReale) {
        this.GioReale = numGioReale;
    }
}
