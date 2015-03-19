package classi;

/**
 * Created by Christian on 04/03/2015.
 */
public class Formazione {

    private String modulo;
    private Squadra squadra;
    private Giocatore[] formazione;

    public Formazione(Giocatore[] g, String modulo, Squadra squadra){
        this.modulo = new String(modulo);
        this.squadra = squadra;
        for (Giocatore i : g) {
            formazione = g;
        }
    }

    //funzione per la dichiarazione della formazione, tramite gui si sceglie il modulo e si compone il vettore con le giuste posizioni
    public void setFormazione(Giocatore[] g, String modulo, Squadra squadra) {
        this.modulo = new String(modulo);
        this.squadra = squadra;
        for (Giocatore i : g) {
            formazione= g;
        }
    }

    public Giocatore[] getFormazione() {
        return formazione;
    }



}
