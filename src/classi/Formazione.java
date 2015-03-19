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
        for (int i=0; i<11; i++) p+=formazione.get(i).getVoti().getMagicVoto(); //somma i punteggi dei titolari
        if (formazione.get(0).getVoti().getVoto()==0) p+=formazione.get(11).getVoti().getMagicVoto(); //fa entrare in campo il portiere di riserva se il titolare non gioca

        if (modulo.equals("3-4-3")) {

                n=0; //controlla quanti difensori sostituire
                for (int i=1; i<4; i++) { n++; }
                if (n>1) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();
                if (n==1 && formazione.get(12).getVoti().getVoto()==0) p+=formazione.get(13).getVoti().getMagicVoto();
                if (n==1) p+=formazione.get(13).getVoti().getMagicVoto();

                n=0; //controlla quanti centrocampisti sostituire
                for (int i=4; i<8; i++) { n++; }
                if (n>1) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();
                if (n==1 && formazione.get(14).getVoti().getVoto()==0) p+=formazione.get(15).getVoti().getMagicVoto();
                if (n==1) p+=formazione.get(15).getVoti().getMagicVoto();

                n=0; //controlla quanti attaccanti sostituire
                for (int i=8; i<11; i++) { n++; }
                if (n>1) p+=formazione.get(16).getVoti().getMagicVoto()+formazione.get(17).getVoti().getMagicVoto();
                if (n==1 && formazione.get(16).getVoti().getVoto()==0) p+=formazione.get(17).getVoti().getMagicVoto();
                if (n==1) p+=formazione.get(17).getVoti().getMagicVoto();
        }

        else if (modulo.equals("3-5-2")) {

            n=0; //controlla quanti difensori sostituire
            for (int i=1; i<4; i++) { n++; }
            if (n>1) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();
            if (n==1 && formazione.get(12).getVoti().getVoto()==0) p+=formazione.get(13).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(13).getVoti().getMagicVoto();

            n=0; //controlla quanti centrocampisti sostituire
            for (int i=4; i<9; i++) { n++; }
            if (n>1) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();
            if (n==1 && formazione.get(14).getVoti().getVoto()==0) p+=formazione.get(15).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(15).getVoti().getMagicVoto();

            n=0; //controlla quanti attaccanti sostituire
            for (int i=9; i<11; i++) { n++; }
            if (n>1) p+=formazione.get(16).getVoti().getMagicVoto()+formazione.get(17).getVoti().getMagicVoto();
            if (n==1 && formazione.get(16).getVoti().getVoto()==0) p+=formazione.get(17).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(17).getVoti().getMagicVoto();
        }

        if (modulo.equals("4-3-3")) {

            n=0; //controlla quanti difensori sostituire
            for (int i=1; i<5; i++) { n++; }
            if (n>1) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();
            if (n==1 && formazione.get(12).getVoti().getVoto()==0) p+=formazione.get(13).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(13).getVoti().getMagicVoto();

            n=0; //controlla quanti centrocampisti sostituire
            for (int i=5; i<8; i++) { n++; }
            if (n>1) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();
            if (n==1 && formazione.get(14).getVoti().getVoto()==0) p+=formazione.get(15).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(15).getVoti().getMagicVoto();

            n=0; //controlla quanti attaccanti sostituire
            for (int i=8; i<11; i++) { n++; }
            if (n>1) p+=formazione.get(16).getVoti().getMagicVoto()+formazione.get(17).getVoti().getMagicVoto();
            if (n==1 && formazione.get(16).getVoti().getVoto()==0) p+=formazione.get(17).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(17).getVoti().getMagicVoto();
        }

        if (modulo.equals("4-4-2")) {

            n=0; //controlla quanti difensori sostituire
            for (int i=1; i<5; i++) { n++; }
            if (n>1) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();
            if (n==1 && formazione.get(12).getVoti().getVoto()==0) p+=formazione.get(13).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(13).getVoti().getMagicVoto();

            n=0; //controlla quanti centrocampisti sostituire
            for (int i=5; i<9; i++) { n++; }
            if (n>1) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();
            if (n==1 && formazione.get(14).getVoti().getVoto()==0) p+=formazione.get(15).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(15).getVoti().getMagicVoto();

            n=0; //controlla quanti attaccanti sostituire
            for (int i=9; i<11; i++) { n++; }
            if (n>1) p+=formazione.get(16).getVoti().getMagicVoto()+formazione.get(17).getVoti().getMagicVoto();
            if (n==1 && formazione.get(16).getVoti().getVoto()==0) p+=formazione.get(17).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(17).getVoti().getMagicVoto();
        }

        if (modulo.equals("4-5-1")) {

            n=0; //controlla quanti difensori sostituire
            for (int i=1; i<5; i++) { n++; }
            if (n>1) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();
            if (n==1 && formazione.get(12).getVoti().getVoto()==0) p+=formazione.get(13).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(13).getVoti().getMagicVoto();

            n=0; //controlla quanti centrocampisti sostituire
            for (int i=5; i<10; i++) { n++; }
            if (n>1) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();
            if (n==1 && formazione.get(14).getVoti().getVoto()==0) p+=formazione.get(15).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(15).getVoti().getMagicVoto();

            if (formazione.get(10).getVoti().getVoto()==0 && formazione.get(16).getVoti().getVoto()!=0) p+=formazione.get(16).getVoti().getMagicVoto();
            if (formazione.get(10).getVoti().getVoto()==0 && formazione.get(16).getVoti().getVoto()==0) p+=formazione.get(17).getVoti().getMagicVoto();
        }

        if (modulo.equals("5-3-2")) {

            n=0; //controlla quanti difensori sostituire
            for (int i=1; i<6; i++) { n++; }
            if (n>1) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();
            if (n==1 && formazione.get(12).getVoti().getVoto()==0) p+=formazione.get(13).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(13).getVoti().getMagicVoto();

            n=0; //controlla quanti centrocampisti sostituire
            for (int i=6; i<9; i++) { n++; }
            if (n>1) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();
            if (n==1 && formazione.get(14).getVoti().getVoto()==0) p+=formazione.get(15).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(15).getVoti().getMagicVoto();

            n=0; //controlla quanti attaccanti sostituire
            for (int i=9; i<11; i++) { n++; }
            if (n>1) p+=formazione.get(16).getVoti().getMagicVoto()+formazione.get(17).getVoti().getMagicVoto();
            if (n==1 && formazione.get(16).getVoti().getVoto()==0) p+=formazione.get(17).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(17).getVoti().getMagicVoto();
        }

        if (modulo.equals("5-4-1")) {

            n=0; //controlla quanti difensori sostituire
            for (int i=1; i<6; i++) { n++; }
            if (n>1) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();
            if (n==1 && formazione.get(12).getVoti().getVoto()==0) p+=formazione.get(13).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(13).getVoti().getMagicVoto();

            n=0; //controlla quanti centrocampisti sostituire
            for (int i=6; i<10; i++) { n++; }
            if (n>1) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();
            if (n==1 && formazione.get(14).getVoti().getVoto()==0) p+=formazione.get(15).getVoti().getMagicVoto();
            if (n==1) p+=formazione.get(15).getVoti().getMagicVoto();

            if (formazione.get(10).getVoti().getVoto()==0 && formazione.get(16).getVoti().getVoto()!=0) p+=formazione.get(16).getVoti().getMagicVoto();
            if (formazione.get(10).getVoti().getVoto()==0 && formazione.get(16).getVoti().getVoto()==0) p+=formazione.get(17).getVoti().getMagicVoto();

        }

        return p;
    }

}
