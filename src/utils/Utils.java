package utils;

import classi.Giocatore;
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
        boolean rsDb = false;
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

            return rsDb = db.inserisciGiocatori(listaGiocatori);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return rsDb;
        } catch (IOException e) {
            e.printStackTrace();
            return rsDb;
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
}
