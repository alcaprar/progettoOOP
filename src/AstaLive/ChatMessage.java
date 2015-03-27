package AstaLive;

import classi.Giocatore;

import java.io.Serializable;

/**
 * Created by Christian on 26/03/2015.
 */
public class ChatMessage implements Serializable{

        /*
        * Il messaggio che si scambiano i client contiene un giocatore e il tempo per effettuare la propria offerta
        */

        private Giocatore giocatore;
        private int tempo = 10;


        ChatMessage(int tipo, Giocatore giocatore, int tempo) {
            this.giocatore = giocatore;
        }

        // getters
        Giocatore getGiocatore() {
            return giocatore;
        }
        int getTempo(){
            return tempo;
        }
}
