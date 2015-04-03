package AstaLive3;

import classi.Giocatore;

import java.io.Serializable;

/**
 * Created by alessandro on 03/04/15.
 */
public class Messaggio implements Serializable {

    protected static final long serialVersionUID = 1112122200L;

    static final int INIZIO_ASTA = 1,
            OFFERTA =2,
            RISPOSTA_OFFERTA=3,
            FINE_OFFERTA=5;


    //OFFERTA serve al server per inviare il giocatore con il prezzo attuale. se è la prima offerta il prezzo è quello inizale meno uno
    //RISPOSTA OFFERTA serve per il client per rispondere con l'offerta che vuol fare
    //FINE OFFERTA serve al server per fare il broadcast che il giocatore è stato acquistato da qualcuno
    //INIZIO ASTA server al server per dire che si inizia

    private int tipo;

    private Giocatore giocatore;

    private int offerta;
    //se è 0 il client rifiuta il giocatore

    private String messaggio;

    private int secondi;

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

    public String getMessaggio(){
        return this.messaggio;
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

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public int getSecondi() {
        return secondi;
    }

    public void setSecondi(int secondi) {
        this.secondi = secondi;
    }
}
