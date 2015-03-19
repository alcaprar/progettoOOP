package classi;

import java.util.ArrayList;

/**
 * Created by Christian on 04/03/2015.
 */
public class Formazione {

    private String modulo;
    private Squadra squadra;
    private ArrayList<Giocatore> formazione;

    public Formazione(ArrayList<Giocatore> g, String modulo, Squadra squadra){
        this.modulo = new String(modulo);
        this.squadra = squadra;
        for (Giocatore i : g) {
            formazione = g;
        }
    }

    //funzione per la dichiarazione della formazione, tramite gui si sceglie il modulo e si compone il vettore con le giuste posizioni
    public void setFormazione(ArrayList<Giocatore> g, String modulo, Squadra squadra) {
        this.modulo = new String(modulo);
        this.squadra = squadra;
        for (Giocatore i : g) {
            formazione= g;
        }
    }

    public ArrayList<Giocatore> getFormazione() {
        return formazione;
    }


    public float calcola () {
        float p=0; //p indica il punteggio della giornata
        int n; //variabile per sapere quanti sono i gicatori da sostituire
        for (int i=0; i<11; i++) p+=formazione[i].getVoto().getMagicVoto(); //somma i punteggi dei titolari
        if (formazione[0].getVoto().getVoto()==0) p+=formazione[11].getVoto().getMagicVoto(); //fa entrare in campo il portiere di riserva se il titolare non gioca
        if (modulo=="3-4-3") {

                n=0; //controlla quanti difensori sostituire
                for (int i=1; i<4; i++) { n++; }
                if (n>1) p+=formazione[12].getVoto().getMagicVoto()+formazione[13].getVoto().getMagicVoto();
                if (n==1 && formazione[12].getVoto().getVoto()==0) p+=formazione[13].getVoto().getMagicVoto();
                if (n==1) p+=formazione[13].getVoto().getMagicVoto();

                n=0; //controlla quanti centrocampisti sostituire
                for (int i=4; i<8; i++) { n++; }
                if (n>1) p+=formazione[14].getVoto().getMagicVoto()+formazione[15].getVoto().getMagicVoto();
                if (n==1 && formazione[14].getVoto().getVoto()==0) p+=formazione[15].getVoto().getMagicVoto();
                if (n==1) p+=formazione[15].getVoto().getMagicVoto();

                n=0; //controlla quanti attaccanti sostituire
                for (int i=8; i<11; i++) { n++; }
                if (n>1) p+=formazione[16].getVoto().getMagicVoto()+formazione[16].getVoto().getMagicVoto();
                if (n==1 && formazione[16].getVoto().getVoto()==0) p+=formazione[16].getVoto().getMagicVoto();
                if (n==1) p+=formazione[17].getVoto().getMagicVoto();
        }
        return p;
    }

}
