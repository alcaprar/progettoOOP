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

    //calcola i risultati della giornata, prende i parametri di cui ha bisogno
    public void calcolaGiornata(int primaFascia, int largFascia, int bonusCasa, Classifica c) {
        for (Partita i : partite ){
            i.calcolaPartita(primaFascia, largFascia, bonusCasa); //chiama il metodo calcola partita su ogni partita della giornata
        }
        for (Partita i : partite ){
            i.aggClassifica(c); //chiama il metodo aggiorna classifica su ogni partita della giornata
        }
    }

    public void calcolaGiornataNew(int primaFascia, int largFascia, int bonusCasa){
        for(Partita part:partite){
            System.out.println(part.getFormCasa().getSquadra().getNome() +" - "+part.getFormOspite().getSquadra().getNome());
            part.calcolaPartitaNew(primaFascia,largFascia,bonusCasa);
        }
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
        Object[][] listaObject = new Object[this.partite.size()][7];

        for(int i=0;i<this.partite.size();i++){
            listaObject[i][0] = String.valueOf(this.partite.get(i).getFormCasa().getSquadra().getNome());
            listaObject[i][1] = String.valueOf(this.partite.get(i).getPuntiCasa());
            listaObject[i][2] = String.valueOf(this.partite.get(i).getGolCasa());
            listaObject[i][3] = "-";
            listaObject[i][4] = String.valueOf(this.partite.get(i).getGolFuori());
            listaObject[i][5] = String.valueOf(this.partite.get(i).getPuntiFuori());
            listaObject[i][6] = String.valueOf(this.partite.get(i).getFormOspite().getSquadra().getNome());
        }
        return listaObject;
    }

    public Object[][] prossimaGiornataToArray(){
        Object[][] listaObject = new Object[this.partite.size()][2];

        for(int i=0; i<this.partite.size();i++){
            listaObject[i][0] = this.partite.get(i).getFormCasa().getSquadra().getNome();
            listaObject[i][1] = this.partite.get(i).getFormOspite().getSquadra().getNome();
        }
        return listaObject;
    }
}
