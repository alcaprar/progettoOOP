package classi;

/**
 * Created by Christian on 04/03/2015.
 */
public class Formazione {

    private String modulo;
    private Squadra squadra;
    private Giocatore[] rosa;

    //funzione per la dichiarazione della formazione, tramite gui si sceglie il modulo e si compone il vettore con le giuste posizioni
    public void setFormazione(Giocatore[] g, String modulo, Squadra squadra) {
        this.modulo = new String(modulo);
        this.squadra = squadra;
        for (Giocatore i : g) {
            rosa = g;
        }
    }

    public Giocatore[] getFormazione() {
        return rosa;
    }


    //da mettere anche nel database i voti dell' ultima giornata in giocatore
    private float calcola (Giocatore[]rosa) { //rosa lo interpreto come formazione se lo interpreto bene da rinominare assolutamente, parentesi quadre?
        float p=0; //p indica il punteggio della giornata
        int r; //variabile per sapere se i panchinari sono già entrati
        for (int i=0; i<11; i++) p+=rosa[i].getVoto(); //somma i punteggi dei titolari
        if (rosa[0].getVoto()==0) p+=rosa[11].getVoto(); //fa entrare in campo il portiere di riserva se il titolare non gioca //da vedere il csv cosa mette per i non voti
        switch (modulo) { //da rivedere sintassi switch
            case 3-4-3: {
                //sostituisco i difensori
                r=12; //indica la posizione del primo difensore in panchina
                if (rosa[1].getVoto()==0) {p+=rosa[r].getVoto(); r++;}
                if (rosa[2].getVoto()==0 && p<14) {p+=rosa[r].getVoto(); r++;} //p controlla che i giocatori della panchina in quel ruolo non sono finiti
                if (rosa[3].getVoto()==0 && p<14) {p+=rosa[r].getVoto(); r++;}
            }
        }
    return p;
    }

    //questo metodo calcola i gol dato il punteggio
    private int numgol (int p) { //le variabili lettere minuscole? nome metodi?
        int g=0;
        for (int i=66; i>p; i+=4)  g++; //66 è l'inizio della prima fascia, 4 è la larghezza della fascia
    }
}
