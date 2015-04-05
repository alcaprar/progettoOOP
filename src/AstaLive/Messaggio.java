package AstaLive;

import classi.Giocatore;
import classi.Persona;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Messaggio che si scambiano Server e Client.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Messaggio implements Serializable {

    protected static final long serialVersionUID = 1112122200L;

    static final int INIZIO_ASTA = 1,
            OFFERTA =2,
            RISPOSTA_OFFERTA=3,
            TEMPO = 4,
            FINE_OFFERTA=5,
            LISTA_GIOCATORI=6,
            FINE_ASTA=7;

    private int tipo;

    private Giocatore giocatore;

    private int offerta;  //se è 0 il client rifiuta il giocatore

    private Persona utente;

    private int secondi;

    private ArrayList<Giocatore> listaGiocatori;
    private ArrayList<Persona> listaPartecipanti;

    public Messaggio(int tipo){
        this.tipo = tipo;
    }

    public int getTipo(){
        return this.tipo;
    }

    public Giocatore getGiocatore(){
        return this.giocatore;
    }

    public int getOfferta(){
        return this.offerta;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setGiocatore(Giocatore giocatore) {
        this.giocatore = giocatore;
    }

    public void setOfferta(int offerta) {
        this.offerta = offerta;
    }

    public int getSecondi() {
        return secondi;
    }

    public void setSecondi(int secondi) {
        this.secondi = secondi;
    }

    public ArrayList<Persona> getListaPartecipanti() {
        return listaPartecipanti;
    }

    public void setListaPartecipanti(ArrayList<Persona> listaPartecipanti) {
        this.listaPartecipanti = listaPartecipanti;
    }

    public ArrayList<Giocatore> getListaGiocatori() {
        return listaGiocatori;
    }

    public void setListaGiocatori(ArrayList<Giocatore> listaGiocatori) {
        this.listaGiocatori = listaGiocatori;
    }

    public Persona getUtente() {
        return utente;
    }

    public void setUtente(Persona utente) {
        this.utente = utente;
    }
}
