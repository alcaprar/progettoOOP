package classi;

import java.util.ArrayList;

/**
 * Created by Christian on 04/03/2015.
 */
public class Formazione {

    private Squadra squadra;
    private String modulo;
    private ArrayList<Voto> listaGiocatori;
    private ArrayList<Giocatore> formazione;

    public Formazione(ArrayList<Giocatore> g, String modulo){
        this.modulo = new String(modulo);
        for (Giocatore i : g) {
            formazione = g;
        }
    }

    public Formazione(Squadra  squadra){
        this.squadra = squadra;
    }

    /*public Formazione(ArrayList<Giocatore> form){
        this.formazione = form;
        if(!formazione.isEmpty()) {
            int d=0;
            int c=0;
            int a=0;
            for (int i = 0; i < 11; i++) {
                if (formazione.get(i).getRuolo() == 'D') d++;
                else if (formazione.get(i).getRuolo() == 'C') c++;
                else if (formazione.get(i).getRuolo() == 'A') a++;
            }
            this.modulo = d + "-" + c + "-" + a;
        }
    }*/
    public Formazione(ArrayList<Voto> form){
        this.listaGiocatori = form;
        if(!listaGiocatori.isEmpty()) {
            int d=0;
            int c=0;
            int a=0;
            for (int i = 0; i < 11; i++) {
                if (listaGiocatori.get(i).getGiocatore().getRuolo() == 'D') d++;
                else if (listaGiocatori.get(i).getGiocatore().getRuolo() == 'C') c++;
                else if (listaGiocatori.get(i).getGiocatore().getRuolo() == 'A') a++;
            }
            this.modulo = d + "-" + c + "-" + a;
        }
    }

    //funzione per la dichiarazione della formazione, tramite gui si sceglie il modulo e si compone il vettore con le giuste posizioni
    public void setFormazione(ArrayList<Giocatore> g, String modulo) {
        this.modulo = new String(modulo);
        for (Giocatore i : g) {
            formazione= g;
        }
    }

    public ArrayList<Giocatore> getFormazione() {
        return formazione;
    }

    //funzione calcola aggiornata
    public float calcolaNew(){
        float p=0;
        int n=0;
        int d=0,c=0,a=0;
        int dGiocato=0,cGiocato=0,aGiocato=0;
        for(int i=0;i<11;i++){
            if(listaGiocatori.get(i).getGiocatore().getRuolo()=='D'){
                d++;
                if(listaGiocatori.get(i).getVoto()!=0){
                    p+=listaGiocatori.get(i).getMagicVoto();
                    dGiocato++;
                }
            }
            else if(listaGiocatori.get(i).getGiocatore().getRuolo()=='C'){
                c++;
                if(listaGiocatori.get(i).getVoto()!=0){
                    p+=listaGiocatori.get(i).getMagicVoto();
                    cGiocato++;
                }
            }
            else if(listaGiocatori.get(i).getGiocatore().getRuolo()=='A'){
                a++;
                if (listaGiocatori.get(i).getVoto()!=0){
                    p+=listaGiocatori.get(i).getMagicVoto();
                    aGiocato++;
                }
            }
        }
        //entra il portiere
        if(listaGiocatori.get(0).getVoto()==0) p+= listaGiocatori.get(11).getMagicVoto();

        //entrano i difensori
        if(dGiocato<d){
            p+=listaGiocatori.get(12).getMagicVoto();
            dGiocato++;
        }
        if(dGiocato<d){
            p+=listaGiocatori.get(13).getMagicVoto();
        }
        //entrano i centrocampisti
        if(cGiocato<c){
            p+=listaGiocatori.get(14).getMagicVoto();
            cGiocato++;
        }
        if(cGiocato<c){
            p+=listaGiocatori.get(15).getMagicVoto();
        }
        //entrano gli attaccanti
        if(aGiocato<a){
            p+=listaGiocatori.get(16).getMagicVoto();
            aGiocato++;
        }
        if(aGiocato<a){
            p+=listaGiocatori.get(17).getMagicVoto();
        }

        return p;

    }

    //calcola il punteggio della formazione
    public float calcola () {
        float p=0; //p indica il punteggio della giornata
        int n; //variabile per sapere quanti sono i gicatori da sostituire
        for (int i=0; i<11; i++) p+=formazione.get(i).getVoti().getMagicVoto(); //somma i punteggi dei titolari
        if (formazione.get(0).getVoti().getVoto()==0) p+=formazione.get(11).getVoti().getMagicVoto(); //fa entrare in campo il portiere di riserva se il titolare non gioca

        //per fare entrare le riserve dobbiamo conoscere i ruoli dei giocatori, quindi dividiamo per modulo
        if (modulo.equals("3-4-3")) {

            n=0; //n indica il numero di difensori da sostituire
            for (int i=1; i<4; i++) { n++; } //controlla quanti difensori sostituire
            //se nen ne deve sostituire nessuno non fa niente
            //se ne deve sostituire uno e il primo panchinaro ha giocato allora entra
            if (n==1 && formazione.get(12).getVoti().getVoto()!=0) p+=formazione.get(12).getVoti().getMagicVoto();
            //se ne deve sostituire uno e il primo panchinaro non ha giocato o ne deve sostituire più di uno allora entrano entrambi i panchinari
            //se anche i panchinari non hanno giocato la squadra gioca in meno, si somma 0 e il punteggio non cambia
            else if (n>0) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();

            n=0; //riinizializza n a 0 e ripete il procedimento per i centrocampisti
            for (int i=4; i<8; i++) { n++; }
            if (n==1 && formazione.get(14).getVoti().getVoto()!=0) p+=formazione.get(14).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();


            n=0; //lo stesso per gli attaccanti
            for (int i=8; i<11; i++) { n++; }
            if (n==1 && formazione.get(16).getVoti().getVoto()!=0) p+=formazione.get(16).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(16).getVoti().getMagicVoto()+formazione.get(17).getVoti().getMagicVoto();
        }

        else if (modulo.equals("3-5-2")) {

            n=0;
            for (int i=1; i<4; i++) { n++; }
            if (n==1 && formazione.get(12).getVoti().getVoto()!=0) p+=formazione.get(12).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();

            n=0;
            for (int i=4; i<9; i++) { n++; }
            if (n==1 && formazione.get(14).getVoti().getVoto()!=0) p+=formazione.get(14).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();

            n=0;
            for (int i=9; i<11; i++) { n++; }
            if (n==1 && formazione.get(16).getVoti().getVoto()!=0) p+=formazione.get(16).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(16).getVoti().getMagicVoto()+formazione.get(17).getVoti().getMagicVoto();
        }

        if (modulo.equals("4-3-3")) {

            n=0;
            for (int i=1; i<5; i++) { n++; }
            if (n==1 && formazione.get(12).getVoti().getVoto()!=0) p+=formazione.get(12).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();

            n=0;
            for (int i=5; i<8; i++) { n++; }
            if (n==1 && formazione.get(14).getVoti().getVoto()!=0) p+=formazione.get(14).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();

            n=0;
            for (int i=8; i<11; i++) { n++; }
            if (n==1 && formazione.get(16).getVoti().getVoto()!=0) p+=formazione.get(16).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(16).getVoti().getMagicVoto()+formazione.get(17).getVoti().getMagicVoto();
        }

        if (modulo.equals("4-4-2")) {

            n=0;
            for (int i=1; i<5; i++) { n++; }
            if (n==1 && formazione.get(12).getVoti().getVoto()!=0) p+=formazione.get(12).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();

            n=0;
            for (int i=5; i<9; i++) { n++; }
            if (n==1 && formazione.get(14).getVoti().getVoto()!=0) p+=formazione.get(14).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();

            n=0;
            for (int i=9; i<11; i++) { n++; }
            if (n==1 && formazione.get(16).getVoti().getVoto()!=0) p+=formazione.get(16).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(16).getVoti().getMagicVoto()+formazione.get(17).getVoti().getMagicVoto();
        }

        if (modulo.equals("4-5-1")) {

            n=0;
            for (int i=1; i<5; i++) { n++; }
            if (n==1 && formazione.get(12).getVoti().getVoto()!=0) p+=formazione.get(12).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();

            n=0;
            for (int i=5; i<10; i++) { n++; }
            if (n==1 && formazione.get(14).getVoti().getVoto()!=0) p+=formazione.get(14).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();

            //poichè abbiamo un solo attaccante non abbiamo bisogno del ciclo
            //se l'attaccante non ha giocato e il primo panchinaro si, allora entra
            if (formazione.get(10).getVoti().getVoto()==0 && formazione.get(16).getVoti().getVoto()!=0) p+=formazione.get(16).getVoti().getMagicVoto();
            //altrimenti entra il secondo
            if (formazione.get(10).getVoti().getVoto()==0 ) p+=formazione.get(17).getVoti().getMagicVoto();
        }

        if (modulo.equals("5-3-2")) {

            n=0;
            for (int i=1; i<6; i++) { n++; }
            if (n==1 && formazione.get(12).getVoti().getVoto()!=0) p+=formazione.get(12).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();

            n=0;
            for (int i=6; i<9; i++) { n++; }
            if (n==1 && formazione.get(14).getVoti().getVoto()!=0) p+=formazione.get(14).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();

            n=0;
            for (int i=9; i<11; i++) { n++; }
            if (n==1 && formazione.get(16).getVoti().getVoto()!=0) p+=formazione.get(16).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(16).getVoti().getMagicVoto()+formazione.get(17).getVoti().getMagicVoto();
        }

        if (modulo.equals("5-4-1")) {

            n=0;
            for (int i=1; i<6; i++) { n++; }
            if (n==1 && formazione.get(12).getVoti().getVoto()!=0) p+=formazione.get(12).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(12).getVoti().getMagicVoto()+formazione.get(13).getVoti().getMagicVoto();

            n=0;
            for (int i=6; i<10; i++) { n++; }
            if (n==1 && formazione.get(14).getVoti().getVoto()!=0) p+=formazione.get(14).getVoti().getMagicVoto();
            else if (n>0) p+=formazione.get(14).getVoti().getMagicVoto()+formazione.get(15).getVoti().getMagicVoto();

            if (formazione.get(10).getVoti().getVoto()==0 && formazione.get(16).getVoti().getVoto()!=0) p+=formazione.get(16).getVoti().getMagicVoto();
            if (formazione.get(10).getVoti().getVoto()==0 && formazione.get(16).getVoti().getVoto()==0) p+=formazione.get(17).getVoti().getMagicVoto();

        }

        return p;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public void setFormazione(ArrayList<Giocatore> formazione) {
        this.formazione = formazione;
    }


    public Object[][] listaFormToArray(){

        Object[][] listaObject = new Object[formazione.size()][2];

        for(int i=0;i<formazione.size();i++){
            listaObject[i][0] = formazione.get(i).getCognome();
            listaObject[i][1] = formazione.get(i).getVoti().getMagicVoto();

        }

        return listaObject;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    public ArrayList<Voto> getListaGiocatori() {
        return listaGiocatori;
    }

    public void setListaGiocatori(ArrayList<Voto> listaGiocatori) {
        this.listaGiocatori = listaGiocatori;
    }
}
