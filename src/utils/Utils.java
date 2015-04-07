package utils;

import entità.*;
import db.Mysql;

import java.awt.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Classe con delle funzioni di utilità.
 * @author Alessandro Caprarelli
 * @author Giacomo Grilli
 * @author Christian Manfredi
 */
public class Utils {

    /**
     * Cast da array di char a stringa.
     * @param arrayChar array di char da convertire
     * @return stringa
     */
    public static String passwordString(char[] arrayChar) {
        return new String(arrayChar);
    }

    /*public boolean csvQuotazioni(String pathFile,  String csvSplitBy){
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

    }*/

    /**
     * Parsing del file xls(versione 2003/2007) con la lista dei giocatori.
     * con le relative quotazioni.
     * @param pathfile
     * @return
     * @throws FileNotFoundException
     */
    public boolean xlsQuotazioni(String pathfile) throws IOException{
        //file da aprire
        File filexls = new File(pathfile);
        Workbook workbook;

        final Mysql db = new Mysql();
        try{
            //creo un oggetto workbook dal file
            workbook = Workbook.getWorkbook(filexls);
            //prendo il primo foglio
            Sheet foglio = workbook.getSheet(0);

            //lista di giocatori
            ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();

            for(int i=0;i<foglio.getRows();i++){
                //controllo che la prima cella della riga sia un numero(ID), cioè che sia una riga con un giocatore
                //e non una riga con le intestazioni e altre scritte
                if(foglio.getCell(0,i).getType()==CellType.NUMBER){
                    int id= Integer.parseInt(foglio.getCell(0,i).getContents());
                    char ruolo = foglio.getCell(1,i).getContents().charAt(0);
                    String cognome = foglio.getCell(2, i).getContents();
                    String squadraReale = foglio.getCell(3, i).getContents();
                    int costo = (int)Float.parseFloat(foglio.getCell(4,i).getContents().replace(',','.'));


                    listaGiocatori.add(new Giocatore(cognome,id,costo,squadraReale,ruolo));
                }

            }

            return db.inserisciGiocatoriAnno(listaGiocatori);
        } catch (BiffException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Formato del file sconosciuto.\nIl file deve essere un file xls < Excel 2007", "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Parsing del file xls( versione 2003/2007) con i voti e inserimento nel db.
     * @param pathFile path del file
     * @param numeroGiornata numero della giornata dei voti che si stanno inserendo.
     * @return true se il parsing e l'inserimento nel db è andato a buon fine
     */
    public boolean xlsvoti(String pathFile, int numeroGiornata) throws IOException{
        //file da aprire
        File filexls = new File(pathFile);
        Workbook workbook;

        final Mysql db = new Mysql();
        try {
            workbook = Workbook.getWorkbook(filexls);
            // prendo il primo foglio
            Sheet foglio = workbook.getSheet(0);

            ArrayList<ArrayList<String>> listaVoti = new ArrayList<ArrayList<String>>();

            //scorro le righe
            for(int i=0;i<foglio.getRows();i++) {
                //controllo che la prima cella della riga sia un numero, cioè è una riga con i voti e non
                //una riga con le intestazioni o altre scritte
                if(foglio.getCell(0,i).getType()==CellType.NUMBER){
                    //se è una riga con i voti la aggiungo all'arraylist
                    ArrayList<String> voto = new ArrayList<String>();
                    voto.add(foglio.getCell(0, i).getContents());
                    voto.add(foglio.getCell(3, i).getContents().replace("\"","").replace("*","").replace(",","."));
                    voto.add(foglio.getCell(4, i).getContents());
                    voto.add(foglio.getCell(5, i).getContents());
                    voto.add(foglio.getCell(6, i).getContents());
                    voto.add(foglio.getCell(7, i).getContents());
                    voto.add(foglio.getCell(8, i).getContents());
                    voto.add(foglio.getCell(9, i).getContents());
                    voto.add(foglio.getCell(10, i).getContents());
                    voto.add(foglio.getCell(11, i).getContents());
                    voto.add(foglio.getCell(12, i).getContents());
                    voto.add(foglio.getCell(13, i).getContents());
                    listaVoti.add(voto);
                }
            }

            return db.inserisciVoti(listaVoti, numeroGiornata) ;
        } catch (BiffException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Formato del file sconosciuto.\nIl file deve essere un file xls < Excel 2007","Errore",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Ritorna un array di object partendo da un array list di giocatori.
     * @param listaGiocatori
     * @return
     */
    public Object[][] listaGiocatoriToArray(ArrayList<Giocatore> listaGiocatori){

        Object[][] listaObject = new Object[listaGiocatori.size()][5];

        for(int i=0;i<listaGiocatori.size();i++){
            listaObject[i][0] = listaGiocatori.get(i).getID();
            listaObject[i][1] = listaGiocatori.get(i).getCognome();
            listaObject[i][2] = listaGiocatori.get(i).getRuolo();
            listaObject[i][3] = listaGiocatori.get(i).getSquadraReale();
            listaObject[i][4]=listaGiocatori.get(i).getPrezzoBase();
        }

        return listaObject;
    }

    /**
     * Ritorna un array di object partendo da un array list di giocatori.
     * @param listaGiocatori
     * @return
     */
    public Object[][] listaGiocatoriToArraySquadre(ArrayList<Giocatore> listaGiocatori){

        Object[][] listaObject = new Object[listaGiocatori.size()][4];

        for(int i=0;i<listaGiocatori.size();i++){
            listaObject[i][0] = listaGiocatori.get(i).getCognome();
            listaObject[i][1] = listaGiocatori.get(i).getRuolo();
            listaObject[i][2] = listaGiocatori.get(i).getSquadraReale();
            listaObject[i][3] = listaGiocatori.get(i).getPrezzoAcquisto();

        }

        return listaObject;
    }

    /**
     * Ritorna un array di object partendo da un array list di giocatori.
     * @param listaGiornate
     * @return
     */
    public Object[][] listaGiornateToArray(ArrayList<GiornataReale> listaGiornate){
        Object[][] listaObject = new Object[listaGiornate.size()][3];

        for(int i=0; i<listaGiornate.size();i++){
            listaObject[i][0]= listaGiornate.get(i).getNumeroGiornata();
            if(listaGiornate.get(i).getDataOraInizio()!=null){
                listaObject[i][1] = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(listaGiornate.get(i).getDataOraInizio());
            }
            if(listaGiornate.get(i).getDataOraFine()!=null){
                listaObject[i][2] = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(listaGiornate.get(i).getDataOraFine());
            }
        }

        return listaObject;
    }

    /**
     * Crea il calendario del campionato passato per parametro.
     * Utilizza l'algoritmo di berger.
     * @param primaGiornata
     * @param ultimaGiornata
     * @param campionato
     * @see #spostaDestra(ArrayList, Squadra, int)
     * @see #spostaSinistra(ArrayList, Squadra, int)
     */
    public  void creaCalendario(int primaGiornata, int ultimaGiornata, Campionato campionato) {
        ArrayList<Giornata> listaGiornate = new ArrayList<Giornata>();

        //numero partecipanti
        int n = campionato.getNumeroPartecipanti();

        //numero di giornate in un girone
        int giornate = n - 1;

        //inizializzo il contatore delle giornate
        int k = 1;

        ArrayList<Squadra> squadreCasa = new ArrayList<Squadra>();
        ArrayList<Squadra> squadreTrasferta = new ArrayList<Squadra>();

        for (int i = 0; i < n / 2; i++) {
            squadreCasa.add(new Squadra(campionato.getListaSquadrePartecipanti().get(i).getID(), campionato.getListaSquadrePartecipanti().get(i).getNome()));
            squadreTrasferta.add(new Squadra(campionato.getListaSquadrePartecipanti().get(n - 1 - i).getID(), campionato.getListaSquadrePartecipanti().get(n - 1 - i).getNome()));
        }

        while (primaGiornata <= ultimaGiornata) {
            for (int i = 0; i < giornate && primaGiornata <= ultimaGiornata; i++) {

                ArrayList<Partita> listaPartite = new ArrayList<Partita>();
                //alterna le partite in casa e fuori
                if (i % 2 == 0) {
                    for (int j = 0; j < n / 2; j++) {
                        listaPartite.add(new Partita(squadreTrasferta.get(j), squadreCasa.get(j), j + 1));
                    }
                } else {
                    for (int j = 0; j < n / 2; j++) {
                        listaPartite.add(new Partita(squadreCasa.get(j), squadreTrasferta.get(j), j + 1));
                    }
                }
                listaGiornate.add(new Giornata(k, new GiornataReale(primaGiornata), listaPartite));

                //bisogna spostare gli elementi delle liste mantendendo fisso il primo elemento
                Squadra pivot = squadreCasa.get(0);
                //sposta in avanti gli elementi di "trasferta" inserendo
                //all'inizio l'elemento casa[1] e salva l'elemento uscente in "riporto"
                Squadra riporto = spostaDestra(squadreTrasferta, squadreCasa.get(1), n);
                // sposta a sinistra gli elementi di "casa" inserendo all'ultimo
                //posto l'elemento "riporto"
                spostaSinistra(squadreCasa, riporto, n);
                //ripristina l'elemento fisso
                squadreCasa.set(0, pivot);

                //incremento i contatori
                k++;
                primaGiornata++;
            }
            //inverto le partite create nel for precedente per creare il "ritorno" del girone
            for (int i = 0; i < giornate && primaGiornata <= ultimaGiornata; i++) {
                ArrayList<Partita> listaPartite = new ArrayList<Partita>();
                for (int j = 0; j < n / 2; j++) {
                    listaPartite.add(new Partita(listaGiornate.get(i).getPartite().get(j).getFormOspite().getSquadra(), listaGiornate.get(i).getPartite().get(j).getFormCasa().getSquadra(), j + 1));
                }
                listaGiornate.add(new Giornata(k, new GiornataReale(primaGiornata), listaPartite));

                //incremento i contatori
                k++;
                primaGiornata++;
            }
        }
        //setto il calendario (l'arraylist di giornate)
        campionato.setCalendario(listaGiornate);
    }

    /**
     * Funzione di utilità per la creazione del campionato.
     * Sposta in avanti tutti gli elementi dell'arraylist e ritorno quello che rimane fuori.
     * @see #creaCalendario(int, int, Campionato)
     * @see #spostaSinistra(ArrayList, Squadra, int)
     * @param trasferta
     * @param casaUno primo elemento della lista casa
     * @param n numero di squadre
     * @return l'elemento che rimane fuori durante lo spostamento
     */
    private Squadra spostaDestra(ArrayList<Squadra> trasferta, Squadra casaUno, int n){
        Squadra riporto = trasferta.get(n/2-1);
        for(int i=n/2-1;i>0;i--){
            trasferta.set(i,trasferta.get(i-1));
        }
        trasferta.set(0, casaUno);
        return riporto;
    }

    /**
     * Funzione di utilità per la creazione del campionato.
     * Sposta indietro tutti gli elementi dell'arraylist.
     * Setta come ultimo elemento quello che è rimasto fuori da spostaSinistra.
     * @see #creaCalendario(int, int, Campionato)
     * @see #spostaDestra(ArrayList, Squadra, int)
     * @param casa
     * @param riporto
     * @param n
     */
    private void spostaSinistra(ArrayList<Squadra> casa, Squadra riporto, int n){
        for(int i=0;i<n/2-1;i++){
            casa.set(i,casa.get(i+1));
        }
        casa.set(n/2-1,riporto);
    }
    
    public static void resizeTable(JTable tabella){
        tabella.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        for (int column = 0; column < tabella.getColumnCount(); column++)
        {
            TableColumn tableColumn = tabella.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < tabella.getRowCount(); row++)
            {
                TableCellRenderer cellRenderer = tabella.getCellRenderer(row, column);
                Component c = tabella.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + tabella.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);


                if (preferredWidth >= maxWidth)
                {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tableColumn.setPreferredWidth( preferredWidth );
        }
    }
}
