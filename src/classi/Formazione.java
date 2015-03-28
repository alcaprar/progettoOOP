package classi;

import java.util.ArrayList;

/**
 * Created by Christian on 04/03/2015.
 */
public class Formazione {

    private Squadra squadra;
    private String modulo;
    private ArrayList<Voto> listaGiocatori;


    public Formazione(Squadra  squadra){
        this.squadra = squadra;
    }


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

    public Formazione(ArrayList<Giocatore> form, String modulo){
        ArrayList<Voto> listaGioc = new ArrayList<Voto>();
        for(Giocatore gioc:form){
            listaGioc.add(new Voto(gioc));
        }
        this.listaGiocatori = listaGioc;
        this.modulo  = modulo;
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


    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }


    public Object[][] listaFormToArray(){

        Object[][] listaObject = new Object[listaGiocatori.size()][2];

        for(int i=0;i<listaGiocatori.size();i++){
            listaObject[i][0] = new String(listaGiocatori.get(i).getGiocatore().getCognome());
            listaObject[i][1] = new Float(listaGiocatori.get(i).getMagicVoto());

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
