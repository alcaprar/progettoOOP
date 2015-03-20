package utils;

import classi.Giornata;
import classi.GiornataReale;
import classi.Partita;

import java.util.ArrayList;

/**
 * Created by alessandro on 20/03/15.
 */
public class CreaCalendario {
    private int n;
    ArrayList<Giornata> listaGiornate = new ArrayList<Giornata>();

    public  void calendario(){
        int primaGiornata = 2;
        int ultimaGiornata = 37;



        String[] squadre = {"1","2","3","4","5","6","7","8","9","10"};

        n = 10;

        int giornate = n-1;

        //giornata
        int k=0;

        String[] casa = new String[n/2];
        String[] trasferta = new String[n/2];

        for(int i =0;i<n/2;i++){
            casa[i] = squadre[i];
            trasferta[i] = squadre[n-1-i];

        }

        while (primaGiornata<=ultimaGiornata) {

            for (int i = 0; i < giornate && primaGiornata <= ultimaGiornata; i++) {
                k++;
                primaGiornata++;
            /* stampa le partite di questa giornata */
                //System.out.println("Giornata: " + k);

                ArrayList<Partita> listaPartite = new ArrayList<Partita>();

        /* alterna le partite in casa e fuori */
                if (i % 2 == 0) {
                    for (int j = 0; j < n / 2; j++) {
                        //System.out.println(String.valueOf(j + 1) + ")" + " " + trasferta[j] + "-" + casa[j]);
                        listaPartite.add(new Partita(trasferta[j], casa[j]));
                    }
                } else {
                    for (int j = 0; j < n / 2; j++) {
                        //System.out.println(String.valueOf(j + 1) + " " + casa[j] + "-" + trasferta[j]);
                        listaPartite.add(new Partita(casa[j], trasferta[j]));
                    }
                }
                listaGiornate.add(new Giornata(k, new GiornataReale(primaGiornata), listaPartite));
                //System.out.println("");

                String pivot = casa[0];

                String riporto = spostaAvanti(trasferta, casa[1]);

                spostaIndietro(casa, riporto);

                casa[0] = pivot;


            }
            for (int i = 0; i < giornate && primaGiornata <= ultimaGiornata; i++) {
                k++;
                primaGiornata++;

                ArrayList<Partita> listaPartite = new ArrayList<Partita>();

                for (int j = 0; j < n / 2; j++) {
                    listaPartite.add(new Partita(listaGiornate.get(i).getPartite().get(j).getOspite(), listaGiornate.get(i).getPartite().get(j).getCasa()));
                }
                listaGiornate.add(new Giornata(k, new GiornataReale(primaGiornata), listaPartite));

            }
        }

        for(Giornata giornata:listaGiornate){
            System.out.println("Giornata: " + giornata.getNumGiornata()+" Giornata reale: "+giornata.getNumGioReale().getNumeroGiornata());
            for(Partita partita:giornata.getPartite()){
                System.out.println(partita.getCasa() + "-" + partita.getOspite());
            }
            System.out.println("");

        }






    }

    private String spostaAvanti(String[] trasferta, String casaUno){
        String riporto =trasferta[n/2-1];
        for(int i=n/2-1;i>0;i--){
            trasferta[i] = trasferta[i-1];
        }
        trasferta[0] = casaUno;

        return riporto;
    }

    private void spostaIndietro(String[] casa, String riporto){
        for(int i=0;i<n/2-1;i++){
            casa[i] = casa[i+1];
        }
        casa[n/2-1] = riporto;
    }



    public static   void main(String args[]){
        CreaCalendario calendario = new CreaCalendario();
        calendario.calendario();

    }

}
