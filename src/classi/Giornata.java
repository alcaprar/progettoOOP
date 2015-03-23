package classi;

import java.util.ArrayList;

/**
 * Created by Giacomo on 18/03/15.
 */
public class Giornata {
    private int ID;
    private int numGiornata;
    private GiornataReale gioReale;
    private ArrayList<Partita> partite;

    public Giornata(int numGiornata, GiornataReale gioReale) {
        this.numGiornata = numGiornata;
        this.gioReale = gioReale;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public GiornataReale getGioReale() {
        return gioReale;
    }

    public void setGioReale(GiornataReale gioReale) {
        this.gioReale = gioReale;
    }

    public ArrayList<Partita> getPartite() {
        return partite;
    }

    public void setPartite(ArrayList<Partita> partite) {
        this.partite = partite;
    }

    public Giornata(int numGiornata,GiornataReale giornataReale, ArrayList<Partita> listaPartite){
        this.numGiornata = numGiornata;
        this.gioReale = giornataReale;
        this.partite = listaPartite;

    }

    public Giornata(int ID, int numGiornata,GiornataReale giornataReale){
        this.ID = ID;
        this.numGiornata = numGiornata;
        this.gioReale = giornataReale;


    }

    //calcola i risultati della giornata
    public void calcolaGiornata(Campionato c) {
        for (Partita i : partite ) i.calcolaPartita(c); //chiama il metodo calcola partita su ogni partita della giornata
    }

    public int getNumGiornata() {
        return numGiornata;
    }

    public void setNumGiornata(int numGiornata) {
        this.numGiornata = numGiornata;
    }

    public GiornataReale getNumGioReale() {
        return gioReale;
    }

    public void setNumGioReale(GiornataReale numGioReale) {
        this.gioReale = numGioReale;
    }

    public Object[][] partiteToArray(){
        Object[][] listaObject = new Object[this.partite.size()][10];

        for(int i=0;i<this.partite.size();i++){
            listaObject[i][0] = String.valueOf(this.partite.get(i).getCasa().getID())+" - "+ String.valueOf(this.partite.get(i).getCasa().getNome());
            listaObject[i][1] = String.valueOf(this.partite.get(i).getPuntiCasa());
            listaObject[i][2] = String.valueOf(this.partite.get(i).getGolCasa());
            listaObject[i][3] = String.valueOf(this.partite.get(i).getGolFuori());
            listaObject[i][4] = String.valueOf(this.partite.get(i).getPuntiFuori());
            listaObject[i][5] = String.valueOf(this.partite.get(i).getOspite().getID())+" - "+ String.valueOf(this.partite.get(i).getOspite().getNome());
        }

        return listaObject;

    }
}
