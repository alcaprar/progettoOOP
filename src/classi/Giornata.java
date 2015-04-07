package classi;

import java.util.ArrayList;

/**
 * Classe per la gestione della giornata di campionato.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Giornata {
    private int ID;
    private int numGiornata;
    private GiornataReale gioReale;
    private ArrayList<Partita> partite;

    /**
     * Costruttore utilizzato per la creazione del calendario.
     * @param numGiornata numero della giornata
     * @param giornataReale giornata reale associata
     * @param listaPartite lista delle partite da disputare nella giornata
     */
    public Giornata(int numGiornata,GiornataReale giornataReale, ArrayList<Partita> listaPartite){
        this.numGiornata = numGiornata;
        this.gioReale = giornataReale;
        this.partite = listaPartite;
    }

    /**
     * Costruttore utilizzato per la selezione da database.
     * @param ID id
     * @param numGiornata numero della giornata
     * @param giornataReale giornata reale associata
     */
    public Giornata(int ID, int numGiornata,GiornataReale giornataReale){
        this.ID = ID;
        this.numGiornata = numGiornata;
        this.gioReale = giornataReale;
    }

    /**
     * Costruttore utilizzato per la selezione delle giornate dallo storico.
     * @param numGiornata numero della giornata
     * @param ID id
     */
    public Giornata(int numGiornata,int ID){
        this.ID = ID;
        this.numGiornata=numGiornata;
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

    /**
     * Metodo per il calcolo dei punteggi di ogni incontro della giornata. Richiama la funzione per il calcolo del
     * punteggio di una partita.
     * @param primaFascia valore di partenza per la prima fascia di punteggio
     * @param largFascia larghezza della fascia di punteggio
     * @param bonusCasa bonus per la squadra che gioca in casa
     */
    public void calcolaGiornata(int primaFascia, int largFascia, int bonusCasa){
        for(Partita part:partite){
            part.calcolaPartita(primaFascia,largFascia,bonusCasa);
        }
    }

    /**
     * Metodo che crea un array di array di oggetti a partire dalla lista delle partite
     * @return array di array di oggetti
     */
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

    /**
     * Metodo che crea un array di array di oggetti a partire dall'oggetto della prossima giornata da disputare
     * @return array di array di oggetti
     */
    public Object[][] prossimaGiornataToArray(){
        Object[][] listaObject = new Object[this.partite.size()][2];

        for(int i=0; i<this.partite.size();i++){
            listaObject[i][0] = this.partite.get(i).getFormCasa().getSquadra().getNome();
            listaObject[i][1] = this.partite.get(i).getFormOspite().getSquadra().getNome();
        }
        return listaObject;
    }
}
