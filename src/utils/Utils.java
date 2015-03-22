package utils;

import classi.*;
import com.sun.corba.se.spi.ior.ObjectKey;
import db.Mysql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alessandro on 04/03/15.
 */
public class Utils {

    //cast char --> string
    public String passwordString(char[] a) {
        return new String(a);
    }

    public boolean csvQuotazioni(String pathFile,  String csvSplitBy){
        BufferedReader br = null;
        final Mysql db = new Mysql();

        String line = "";

        ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();
        try {

            br = new BufferedReader(new FileReader(pathFile));

            while ((line = br.readLine()) != null) {

                String[] giocatore = line.split(csvSplitBy);

                listaGiocatori.add(new Giocatore(giocatore[2],Integer.parseInt(giocatore[0]),Integer.parseInt(giocatore[4]),giocatore[3],giocatore[1].charAt(0)));

            }

            if(listaGiocatori.isEmpty()) System.out.print("vuota");
            else System.out.print("piena");

            return db.inserisciGiocatoriAnno(listaGiocatori);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public Object[][] listaGiocatoriToArray(ArrayList<Giocatore> listaGiocatori){

        Object[][] listaObject = new Object[listaGiocatori.size()][5];

        for(int i=0;i<listaGiocatori.size();i++){
            listaObject[i][0] = listaGiocatori.get(i).getID();
            listaObject[i][1] = listaGiocatori.get(i).getCognome();
            listaObject[i][2] = listaGiocatori.get(i).getRuolo();
            listaObject[i][3] = listaGiocatori.get(i).getSquadraReale();
            listaObject[i][4 ]=listaGiocatori.get(i).getPrezzoBase();

        }

        return listaObject;
    }

    public Object[][] listaGiornateToArray(ArrayList<GiornataReale> listaGiornate){
        Object[][] listaObject = new Object[listaGiornate.size()][5];

        for(int i=0; i<listaGiornate.size();i++){
            listaObject[i][0]= listaGiornate.get(i).getNumeroGiornata();
            listaObject[i][1] = listaGiornate.get(i).getDataInizio();
            listaObject[i][2] = listaGiornate.get(i).getOraInizio();
            listaObject[i][3] = listaGiornate.get(i).getDataFine();
            listaObject[i][4] = listaGiornate.get(i).getOraFine();

        }

        return listaObject;
    }

    public Object[][] listaClassificaToArrayPiccola(ArrayList<Classifica> listaClassifica){

        Object[][] listaObject = new Object[listaClassifica.size()][2];

        for(int i=0;i<listaClassifica.size();i++){
            listaObject[i][0] = listaClassifica.get(i).getSquadra().getNome();
            listaObject[i][1] = listaClassifica.get(i).getPunti();
        }

        return listaObject;
    }


    public Object[][] listaClassificaToArray(ArrayList<Classifica> listaClassifica){

        Object[][] listaObject = new Object[listaClassifica.size()][10];

        for(int i=0;i<listaClassifica.size();i++){
            listaObject[i][0] = listaClassifica.get(i).getSquadra().getNome();
            listaObject[i][1] = listaClassifica.get(i).getGiocate();
            listaObject[i][2] = listaClassifica.get(i).getVinte();
            listaObject[i][3] = listaClassifica.get(i).getPareggiate();
            listaObject[i][4] = listaClassifica.get(i).getPerse();
            listaObject[i][5] = listaClassifica.get(i).getDiffReti();
            listaObject[i][6] = listaClassifica.get(i).getGolFatti();
            listaObject[i][7] = listaClassifica.get(i).getGolSubiti();
            listaObject[i][8] = listaClassifica.get(i).getPunteggio();
            listaObject[i][9] = listaClassifica.get(i).getPunti();


        }

        return listaObject;
    }

    //crea il calendario
    public  void creaCalendario(int primaGiornata, int ultimaGiornata, Campionato campionato){
        //int primaGiornata = 2;
        //int ultimaGiornata = 35;
        ArrayList<Giornata> listaGiornate = new ArrayList<Giornata>();



        //String[] squadre = {"1","2","3","4","5","6","7","8","9","10"};

        //numero partecipanti
        int n = campionato.getNumeroPartecipanti();

        //numero di giornate in un girone
        int giornate = n-1;

        //inizializzo il contatore delle giornate
        int k=1;

        //creo due array di stringhe per creare il calendario
        //String[] casa = new String[n/2];
        //String[] trasferta = new String[n/2];

        int[] casa = new int[n/2];
        int[] trasferta = new int[n/2];

        for(int i =0;i<n/2;i++){
            casa[i] =campionato.getListaSquadrePartecipanti().get(i).getID();
            trasferta[i]= campionato.getListaSquadrePartecipanti().get(n-1-i).getID();
            //casa[i] = squadre[i];
            //trasferta[i] = squadre[n-1-i];

        }

        while (primaGiornata<=ultimaGiornata) {

            for (int i = 0; i < giornate && primaGiornata <= ultimaGiornata; i++) {

                ArrayList<Partita> listaPartite = new ArrayList<Partita>();

                if (i % 2 == 0) {
                    for (int j = 0; j < n / 2; j++) {
                        listaPartite.add(new Partita(trasferta[j], casa[j],j+1));
                    }
                } else {
                    for (int j = 0; j < n / 2; j++) {
                        listaPartite.add(new Partita(casa[j], trasferta[j],j+1));
                    }
                }
                listaGiornate.add(new Giornata(k, new GiornataReale(primaGiornata), listaPartite));
                //System.out.println("");

                int pivot = casa[0];

                int riporto = spostaDestra(trasferta, casa[1],n);

                spostaSinistra(casa, riporto,n);

                casa[0] = pivot;

                k++;
                primaGiornata++;


            }
            for (int i = 0; i < giornate && primaGiornata <= ultimaGiornata; i++) {


                ArrayList<Partita> listaPartite = new ArrayList<Partita>();

                for (int j = 0; j < n / 2; j++) {
                    listaPartite.add(new Partita(listaGiornate.get(i).getPartite().get(j).getIDospite(), listaGiornate.get(i).getPartite().get(j).getIDcasa(),j+1));
                }
                listaGiornate.add(new Giornata(k, new GiornataReale(primaGiornata), listaPartite));

                k++;
                primaGiornata++;

            }
        }

        campionato.setCalendario(listaGiornate);

        for(Giornata giornata:listaGiornate){
            System.out.println("Giornata: " + giornata.getNumGiornata()+" Giornata reale: "+giornata.getNumGioReale().getNumeroGiornata());
            for(Partita partita:giornata.getPartite()){
                System.out.println(partita.getIDcasa() + "-" + partita.getIDospite());
            }
            System.out.println("");

        }






    }

    private int spostaDestra(int[] trasferta, int casaUno, int n){
        int riporto =trasferta[n/2-1];
        for(int i=n/2-1;i>0;i--){
            trasferta[i] = trasferta[i-1];
        }
        trasferta[0] = casaUno;

        return riporto;
    }

    private void spostaSinistra(int[] casa, int riporto, int n){
        for(int i=0;i<n/2-1;i++){
            casa[i] = casa[i+1];
        }
        casa[n/2-1] = riporto;
    }
}
