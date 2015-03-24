package db;


import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

import classi.*;
import classi.Classifica;
import classi.Giornata;
import interfacce.*;
import utils.*;

public class Mysql{
    static final private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final private String DB_URL = "jdbc:mysql://db4free.net/progogg";
    static final private String USER = "progogg";
    static final private String PASS = "pagliarecci";
    private Utils utils = new Utils();

    public boolean login(Persona utente) throws SQLException,ClassNotFoundException{
        Connection conn = null ;
        PreparedStatement login = null;
        String loginSql = "SELECT * FROM Utente where Nickname=? and Password=?";
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            login = conn.prepareStatement(loginSql);
            login.setString(1,utente.getNickname());
            login.setString(2,utente.getPassword());
            ResultSet rs = login.executeQuery();
            if(rs.next()){
                utente.setNome(rs.getString("Nome"));
                utente.setCognome(rs.getString("Cognome"));
                utente.setEmail(rs.getString("Email"));
                return true;
            }
            else return false;
        }finally {
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }


        }

    public boolean registra(Persona utente)throws SQLException, ClassNotFoundException{
        Connection conn = null ;
        PreparedStatement registra = null;
        String registraSql ="INSERT into Utente value(?,?,?,?,?,?)";
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            registra = conn.prepareStatement(registraSql);
            registra.setString(1,utente.getNickname());
            registra.setString(2,utente.getPassword());
            registra.setString(3,utente.getNome());
            registra.setString(4,utente.getCognome());
            registra.setString(5,utente.getEmail());
            registra.setString(6,"u");  //tipo: utente
            int rs = registra.executeUpdate();
            if(rs==1){
                //registraTrue(registraForm);
                return true;
            }
            else return false;
        }finally {
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }

    }

    //trova tutti gli utenti disponibili
    public ArrayList<Persona> selectUtenti(){
        Connection conn=null ;
        PreparedStatement stmt = null ;
        String contaString = "SELECT count(*) from Utente where TipoUtente='u'";
        String utentiString = "SELECT * from Utente where TipoUtente='u'";

        //String[] utenti=null;
        ArrayList<Persona> listaUtenti = new ArrayList<Persona>();
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.prepareStatement(contaString);

            ResultSet rscount = stmt.executeQuery();
            rscount.next();
            int numeroUtenti = rscount.getInt("count(*)");
            //utenti = new String[numeroUtenti];

            if(numeroUtenti!=0){
                stmt = conn.prepareStatement(utentiString);
                ResultSet rs = stmt.executeQuery();
                int i =0;
                while(rs.next()){
                    listaUtenti.add(new Persona(rs.getString("Nickname"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Email")));
                    i++;
                }
            }


            return  listaUtenti;

        }catch(SQLException se){
            se.printStackTrace();
            return listaUtenti;

        }catch(Exception e){
            e.printStackTrace();
            return listaUtenti;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    //ignored
                }
            }
        }

    }

    public ArrayList<Giocatore> selectGiocatoriAdmin(){
        Connection conn = null;
        PreparedStatement giocatoristmt = null;
        String giocatoriSql ="SELECT * from CalciatoreAnno";

        PreparedStatement contastmt = null;
        String contaSql ="SELECT count(*) from CalciatoreAnno";

        ArrayList<Giocatore> giocatori = new ArrayList<Giocatore>();

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            contastmt = conn.prepareStatement(contaSql);
            ResultSet rscount = contastmt.executeQuery();
            rscount.next();
            int numeroGiocaori = rscount.getInt("count(*)");


            if(numeroGiocaori!=0) {
                giocatoristmt = conn.prepareStatement(giocatoriSql);
                ResultSet rs = giocatoristmt.executeQuery();
                int i = 0;
                while (rs.next()) {
                    giocatori.add(new Giocatore(rs.getString("Cognome"), rs.getInt("ID"), rs.getInt("Costo"), rs.getString("SqReale"), rs.getString("Ruolo").charAt(0)));
                    i++;
                }
            }
            return  giocatori;

        }catch(SQLException se){
            se.printStackTrace();
            return giocatori;

        }catch(Exception e){
            e.printStackTrace();
            return giocatori;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    //ignored
                }
            }
        }
    }

    public ArrayList<Giocatore> selectGiocatori(Squadra squadra){
        Connection conn = null;
        PreparedStatement listaGiocatoristmt = null;
        String listaGiocatoriSql = "select IDcalcAnno,PrezzoPagato,Costo,SqReale,Cognome,Ruolo from Tesseramento JOIN Fantasquadra on Fantasquadra.ID=Tesseramento.IDsq JOIN CalciatoreAnno on CalciatoreAnno.ID=Tesseramento.IDcalcAnno where IDsq=?";

        int IDsq = squadra.getID();
        ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            listaGiocatoristmt = conn.prepareStatement(listaGiocatoriSql);
            listaGiocatoristmt.setInt(1,IDsq);

            ResultSet rs = listaGiocatoristmt.executeQuery();

            while(rs.next()){
                int ID = rs.getInt("IDcalcAnno");
                String cognome = rs.getString("Cognome");
                int costo = rs.getInt("Costo");
                int prezzoPagato = rs.getInt("PrezzoPagato");
                String squadraReale = rs.getString("SqReale");
                char ruolo=rs.getString("Ruolo").charAt(0);
                Giocatore giocatore = new Giocatore(ID,cognome,costo,prezzoPagato,squadraReale,ruolo);
                listaGiocatori.add(giocatore);
            }

            return listaGiocatori;


        }catch(SQLException se){
            se.printStackTrace();
            return listaGiocatori;

        }catch(Exception e){
            e.printStackTrace();
            return listaGiocatori;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    //ignored
                }
            }
        }
    }

    public ArrayList<Squadra> selectSquadre(Persona utente){
        Connection conn = null;
        PreparedStatement squadrestmt = null;
        String squadreSql ="SELECT * from Iscrizione JOIN Fantasquadra on Iscrizione.IDsq=Fantasquadra.ID JOIN Campionato on Iscrizione.Campionato = Campionato.Nome JOIN Regolamento on Campionato.Nome=Regolamento.NomeCampionato where NickUt=?";

        PreparedStatement partecipantistmt = null;
        String partecipantiSql ="SELECT Fantasquadra.ID, Fantasquadra.Nome, Fantasquadra.NickUt from Iscrizione JOIN Fantasquadra on Iscrizione.IDsq=Fantasquadra.ID JOIN Campionato on Iscrizione.Campionato = Campionato.Nome JOIN Regolamento on Campionato.Nome=Regolamento.NomeCampionato where Campionato=?";

        ArrayList<Squadra> listaSquadre = new ArrayList<Squadra>();
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            squadrestmt = conn.prepareStatement(squadreSql);
            squadrestmt.setString(1,utente.getNickname());
            ResultSet rs = squadrestmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                partecipantistmt = conn.prepareStatement(partecipantiSql);
                partecipantistmt.setString(1,rs.getString("Campionato"));
                ResultSet rspartecipanti = partecipantistmt.executeQuery();

                ArrayList<Squadra> listaSquadrePartecipanti = new ArrayList<Squadra>();

                while(rspartecipanti.next()){
                    listaSquadrePartecipanti.add(new Squadra(rspartecipanti.getInt("ID"),rspartecipanti.getString("Nome"),new Persona(rspartecipanti.getString("NickUt"))));
                }
                Squadra squadra = new Squadra(rs.getInt("ID"),rs.getString("Nome"),utente,new Campionato(rs.getString("Campionato"),rs.getInt("NrPartecipanti"),rs.getBoolean("Asta"),rs.getInt("GiornataInizio"),rs.getInt("GiornataFine"),rs.getInt("CreditiIniziali"),rs.getInt("OrarioConsegna"),rs.getInt("PrimaFascia"),rs.getInt("LargFascia"),rs.getInt("BonusCasa"),new Persona(rs.getString("Presidente")),listaSquadrePartecipanti,rs.getBoolean("GiocatoriDaInserire"),rs.getInt("ProssimaGiornata")),rs.getInt("CreditiDisponibili"));
                listaSquadre.add(squadra);
                i++;
            }

            return  listaSquadre;

        }catch(SQLException se){
            se.printStackTrace();
            return listaSquadre;

        }catch(Exception e){
            e.printStackTrace();
            return listaSquadre;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    //ignored
                }
            }
        }

    }

    public ArrayList<Classifica> selectClassifica(Campionato campionato){
        Connection conn = null;
        PreparedStatement classificastmt = null;
        String classificaSql ="SELECT * from Classifica JOIN Fantasquadra on Classifica.IDsq=Fantasquadra.ID where NomeCampionato=?";

        ArrayList<Classifica> classifica = new ArrayList<Classifica>();

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);


            classificastmt = conn.prepareStatement(classificaSql);
            classificastmt.setString(1,campionato.getNome());
            ResultSet rs = classificastmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                Squadra squadra = new Squadra(rs.getInt("ID"),rs.getString("Nome"));
                int vinte = rs.getInt("Vinte");
                int pareggiate = rs.getInt("Pareggiate");
                int perse = rs.getInt("Perse");
                int punti = rs.getInt("Punti");
                float punteggio = rs.getInt("SommaPunteggi");
                int golFatti = rs.getInt("GolF");
                int golSubiti = rs.getInt("GolS");
                int giocate = vinte+pareggiate+perse;
                int diffReti = golFatti-golSubiti;
                classifica.add(new Classifica(squadra,giocate,vinte,perse,pareggiate,golFatti,golSubiti,diffReti,punteggio,punti));
                i++;
            }

            return  classifica;

        }catch(SQLException se){
            se.printStackTrace();
            return classifica;

        }catch(Exception e){
            e.printStackTrace();
            return classifica;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    //ignored
                }
            }
        }

    }

    public void aggiornaNomeSquadra(Squadra squadra){
        Connection conn = null;
        PreparedStatement aggiornaNome = null;
        String aggiornaNomeSql = "UPDATE Fantasquadra set Nome =? where ID=?";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            aggiornaNome = conn.prepareStatement(aggiornaNomeSql);
            aggiornaNome.setString(1,squadra.getNome());
            aggiornaNome.setInt(2,squadra.getID());
            int rs = aggiornaNome.executeUpdate();


        }catch(SQLException se){
            se.printStackTrace();


        }catch(Exception e){
            e.printStackTrace();


        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    //ignored
                }
            }
        }

    }

    public void aggiornaNomeUtente(Persona utente){
            Connection conn = null;
            PreparedStatement aggiornastmt = null;
            String aggiornaSql = "UPDATE Utente set Nome =? where Nickname=?";

            try{
                //registra il JBCD driver
                Class.forName(JDBC_DRIVER);
                //apre la connessionename
                conn = DriverManager.getConnection(DB_URL,USER,PASS);

                aggiornastmt = conn.prepareStatement(aggiornaSql);
                aggiornastmt.setString(1,utente.getNome());
                aggiornastmt.setString(2,utente.getNickname());
                int rs = aggiornastmt.executeUpdate();


            }catch(SQLException se){
                se.printStackTrace();


            }catch(Exception e){
                e.printStackTrace();


            }finally {
                if(conn!=null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        //ignored
                    }
                }
            }
    }

    public void aggiornaCognomeUtente(Persona utente){
        Connection conn = null;
        PreparedStatement aggiornastmt = null;
        String aggiornaSql = "UPDATE Utente set Cognome =? where Nickname=?";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessionename
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            aggiornastmt = conn.prepareStatement(aggiornaSql);
            aggiornastmt.setString(1,utente.getCognome());
            aggiornastmt.setString(2,utente.getNickname());
            int rs = aggiornastmt.executeUpdate();


        }catch(SQLException se){
            se.printStackTrace();


        }catch(Exception e){
            e.printStackTrace();


        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    //ignored
                }
            }
        }
    }

    public ArrayList<GiornataReale> selectGiornateAdmin(){
        Connection conn = null;
        PreparedStatement giocatoristmt = null;
        String giocatoriSql ="SELECT * from GiornataAnno";

        PreparedStatement contastmt = null;
        String contaSql ="SELECT count(*) from GiornataAnno";

        ArrayList<GiornataReale> giornate = new ArrayList<GiornataReale>();
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            contastmt = conn.prepareStatement(contaSql);
            ResultSet rscount = contastmt.executeQuery();
            rscount.next();
            int numeroGiornate = rscount.getInt("count(*)");


            if(numeroGiornate!=0) {
                giocatoristmt = conn.prepareStatement(giocatoriSql);
                ResultSet rs = giocatoristmt.executeQuery();
                int i = 0;
                while (rs.next()) {
                    giornate.add(new GiornataReale(rs.getInt("NrGioReale"),rs.getDate("DataInizio"),rs.getDate("OraInizio"),rs.getDate("DataFine"),rs.getDate("OraFine")));
                    i++;
                }
            }
            return  giornate;

        }catch(SQLException se){
            se.printStackTrace();
            return giornate;

        }catch(Exception e){
            e.printStackTrace();
            return giornate;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    //ignored
                }
            }
        }

    }

    public ArrayList<Giornata> selectGiornate(Campionato campionato)
    {
        Connection conn = null;
        PreparedStatement giornatastmt = null;
        //String giornataSql = "SELECT * from Giornata JOIN GiornataAnno on Giornata.NrGioReale=GiornataAnno.NrGioReale where NomeCampionato=?";
        String giornataSql = "SELECT * from Giornata where NomeCampionato=?";

        PreparedStatement partitastmt = null;
        String partitaSql = "SELECT P.ID, P.NrPart, P.PunteggioCasa,P.PunteggioOspite,P.GolCasa,P.GolOspite,FC.ID as FCID,FO.ID as FOID,FC.Nome as FCNome, FO.Nome as FONome FROM Partita as P JOIN Fantasquadra as FC on P.IDFantasquadraCasa=FC.ID JOIN Fantasquadra as FO on P.IDFantasquadraOspite=FO.ID where IDgiorn=?";

        ArrayList<Giornata> listaGiornate = new ArrayList<Giornata>();
        try {
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            giornatastmt = conn.prepareStatement(giornataSql);
            giornatastmt.setString(1,campionato.getNome());
            ResultSet rsgiornata = giornatastmt.executeQuery();
            while(rsgiornata.next()){
                Giornata giornata = new Giornata(rsgiornata.getInt("ID"),rsgiornata.getInt("Nrgio"),new GiornataReale(rsgiornata.getInt("NrGioReale")));

                partitastmt = conn.prepareStatement(partitaSql);
                partitastmt.setInt(1,giornata.getID());

                ResultSet rspartita = partitastmt.executeQuery();

                ArrayList<Partita> listaPartite = new ArrayList<Partita>();
                while(rspartita.next()){
                    Squadra squadraCasa =new Squadra(rspartita.getInt("FCID"), rspartita.getString("FCNome"));
                    Squadra squadraOspite = new Squadra(rspartita.getInt("FOID"),rspartita.getString("FONome"));
                    Partita partita = new Partita(rspartita.getInt("ID"),rspartita.getInt("NrPart"),squadraCasa,squadraOspite,rspartita.getInt("GolCasa"),rspartita.getInt("GolOspite"),rspartita.getFloat("PunteggioCasa"),rspartita.getFloat("PunteggioOspite"));
                    listaPartite.add(partita);

                }

                giornata.setPartite(listaPartite);

                listaGiornate.add(giornata);

            }

            return listaGiornate;

        }catch(SQLException se){
            se.printStackTrace();
            return listaGiornate;

        }catch(Exception e){
            e.printStackTrace();
            return listaGiornate;

        }finally {
                if(conn!=null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        //ignored
                    }
                }
        }
    }

    public int selectGiornateVotiInseriti(){
        Connection conn = null;
        PreparedStatement votistmt = null;
        String votiSql = "select NrGioReale from Voto group by NrGioReale order by NrGioReale desc limit 1";

        int giornata=0;

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            votistmt = conn.prepareStatement(votiSql);
            ResultSet rs = votistmt.executeQuery();

            if(rs.next()) giornata = rs.getInt("NrGioReale");


            return giornata;

        }catch(SQLException se){
            se.printStackTrace();
            return giornata;

        }catch(Exception e){
            e.printStackTrace();
            return giornata;

        }finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    //ignored
                }
            }
        }
    }

    //crea il campionato
    public boolean creaCampionato(Campionato campionato){
        Connection conn = null ;
        PreparedStatement campionatostmt = null;
        String campionatoSql ="INSERT into Campionato value(?,?,?,?,?)";

        PreparedStatement regolamentstmt = null;
        String regolamentoSql = "INSERT into Regolamento value(?,?,?,?,?,?,?,?,?)";

        PreparedStatement iscrizionestmt = null;
        String iscrizioneSql = "INSERT into Fantasquadra(NickUt) value(?)";

        PreparedStatement partecipantistmt = null;
        String partecipantiSql = "INSERT into Iscrizione value(?,?,?)";

        PreparedStatement giornatastmt = null;
        String giornataSql = "INSERT into Giornata(NomeCampionato, NrGio, NrGioReale) value(?,?,?)";

        PreparedStatement partitastmt = null;
        String partitaSql = "INSERT into Partita(IDgiorn,NrPart,IDFantasquadraCasa,IDFantasquadraOspite) value(?,?,?,?)";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //inserisco il campionato
            campionatostmt = conn.prepareStatement(campionatoSql);
            campionatostmt.setString(1,campionato.getNome());
            campionatostmt.setInt(2, campionato.getNumeroPartecipanti());
            campionatostmt.setBoolean(3, campionato.isAstaLive());
            campionatostmt.setString(4,campionato.getPresidente().getNickname());
            campionatostmt.setBoolean(5,campionato.isGiocatoriDaInserire());
            int rscampionato = campionatostmt.executeUpdate();

            if(rscampionato==1) {

                //inserisco il regolamento
                regolamentstmt = conn.prepareStatement(regolamentoSql);
                regolamentstmt.setString(1, campionato.getNome());
                regolamentstmt.setInt(2, campionato.getGiornataInizio());
                regolamentstmt.setInt(3, campionato.getGiornataFine());
                regolamentstmt.setInt(4, campionato.getCreditiIniziali());
                regolamentstmt.setInt(5, campionato.getOrarioConsegna());
                regolamentstmt.setInt(6, campionato.getPrimaFascia());
                regolamentstmt.setInt(7, campionato.getLargFascia());
                regolamentstmt.setInt(8, campionato.getBonusCasa());
                regolamentstmt.setInt(9, 0);
                int rsregolamento = regolamentstmt.executeUpdate();

                //creazione squadre e iscrizione
                for (int i = 0; i < campionato.getListaSquadrePartecipanti().size(); i++) {
                    //creo la nuova squadra
                    iscrizionestmt = conn.prepareStatement(iscrizioneSql, Statement.RETURN_GENERATED_KEYS);
                    iscrizionestmt.setString(1, campionato.getListaSquadrePartecipanti().get(i).getProprietario().getNickname());
                    iscrizionestmt.executeUpdate();
                    //trovo l'id della squadra appena inserita
                    ResultSet rs = iscrizionestmt.getGeneratedKeys();
                    rs.next();
                    campionato.getListaSquadrePartecipanti().get(i).setID(rs.getInt(1));

                    //iscrivo la squadra appena creata
                    partecipantistmt = conn.prepareStatement(partecipantiSql);
                    partecipantistmt.setString(1, campionato.getNome());
                    partecipantistmt.setInt(2, campionato.getCreditiIniziali());
                    partecipantistmt.setInt(3, campionato.getListaSquadrePartecipanti().get(i).getID());
                    int rsPartecipanti = partecipantistmt.executeUpdate();
                }

                //creazione e inserimento calendario
                utils.creaCalendario(campionato.getGiornataInizio(),campionato.getGiornataFine(),campionato);


                //inserimento delle giornate
                for(Giornata giornata : campionato.getCalendario()){
                    //inserisco la giornata
                    giornatastmt = conn.prepareStatement(giornataSql, Statement.RETURN_GENERATED_KEYS);
                    giornatastmt.setString(1,campionato.getNome());
                    giornatastmt.setInt(2,giornata.getNumGiornata());
                    giornatastmt.setInt(3,giornata.getGioReale().getNumeroGiornata());
                    giornatastmt.executeUpdate();
                    //trovo l'id della giornata appena inserita
                    ResultSet rs = giornatastmt.getGeneratedKeys();
                    rs.next();
                    giornata.setID(rs.getInt(1));

                    //inserisco le partite della giornata
                    for(Partita partita:giornata.getPartite()){
                        //inserisco la singola partita
                        partitastmt = conn.prepareStatement(partitaSql);
                        partitastmt.setInt(1,giornata.getID());
                        partitastmt.setInt(2,partita.getNumeroPartita());
                        partitastmt.setInt(3,partita.getCasa().getID());
                        partitastmt.setInt(4,partita.getOspite().getID());

                        partitastmt.executeUpdate();
                    }
                }
            }

            if(rscampionato==1){
                return true;
            }
            else return false;
        }catch(SQLException se){
            if(se.getErrorCode()==1062){
            }
            System.out.println(se.getErrorCode());
            se.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally {
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }

    }

    public boolean inserisciGiocatoriAnno(ArrayList<Giocatore> listaGiocatori){
        Connection conn = null;
        PreparedStatement giocatorestmt = null;
        String giocatoreSql = "INSERT into CalciatoreAnno value(?,?,?,?,?)";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            int rsgiocatore=1;

            for(int i =0;i<listaGiocatori.size() && rsgiocatore==1;i++) {
                giocatorestmt = conn.prepareStatement(giocatoreSql);
                giocatorestmt.setInt(1, listaGiocatori.get(i).getID());
                giocatorestmt.setString(2,Character.toString(listaGiocatori.get(i).getRuolo()));
                giocatorestmt.setString(3,listaGiocatori.get(i).getCognome());
                giocatorestmt.setString(4,listaGiocatori.get(i).getSquadraReale());
                giocatorestmt.setInt(5,listaGiocatori.get(i).getPrezzoBase());

                rsgiocatore= giocatorestmt.executeUpdate();
            }

            if(rsgiocatore==1){
                return true;
            }
            else{
                return false;
            }


        }catch(SQLException se){
            se.printStackTrace();
            return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;

        }finally {
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public boolean inserisciGiocatori(Campionato campionato){
        Connection conn = null;
        PreparedStatement giocatorestmt = null;
        String giocatoreSql = "INSERT into Tesseramento value(?,?,?,?)";

        PreparedStatement soldiDisponibilistmt = null;
        String soldiDisponibiliSql = "UPDATE Iscrizione set CreditiDisponibili=? where IDsq=?";

        PreparedStatement aggiornaInfoCampionatostmt = null;
        String aggiornaInfoCampionatoSql = "UPDATE Campionato set GiocatoriDaInserire=?";
        int rs=0;
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String nomeCampionato = campionato.getNome();

            for(int i=0;i<campionato.getListaSquadrePartecipanti().size();i++){
                int IDsq = campionato.getListaSquadrePartecipanti().get(i).getID();
                soldiDisponibilistmt = conn.prepareStatement(soldiDisponibiliSql);
                soldiDisponibilistmt.setInt(1,campionato.getListaSquadrePartecipanti().get(i).getSoldiDisponibili());
                soldiDisponibilistmt.setInt(2,IDsq);
                soldiDisponibilistmt.executeUpdate();
                for(int k=0;k<25;k++){
                    int IDcalc = campionato.getListaSquadrePartecipanti().get(i).getGiocatori().get(k).getID();
                    int prezzoPagato = campionato.getListaSquadrePartecipanti().get(i).getGiocatori().get(k).getPrezzoAcquisto();

                    giocatorestmt = conn.prepareStatement(giocatoreSql);
                    giocatorestmt.setInt(1,IDcalc);
                    giocatorestmt.setString(2,nomeCampionato);
                    giocatorestmt.setInt(3,prezzoPagato);
                    giocatorestmt.setInt(4,IDsq);

                    rs = giocatorestmt.executeUpdate();
                }
            }
            aggiornaInfoCampionatostmt = conn.prepareStatement(aggiornaInfoCampionatoSql);
            aggiornaInfoCampionatostmt.setBoolean(1,false);
            aggiornaInfoCampionatostmt.executeUpdate();

            if(rs==1)return true;
            else return false;
        }catch(SQLException se){
        se.printStackTrace();
        return false;

        }catch(Exception e){
        e.printStackTrace();
        return false;

        }finally {
        try { conn.close(); } catch (Exception e) { /* ignored */ }
        }

    }

    public boolean inserisciVoti(ArrayList<ArrayList<String>> listaVoti, int numeroGiornta){
        Connection conn = null;
        PreparedStatement votostmt=null;
        String votoSql ="INSERT into Voto value(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            int rs=0;

            for(ArrayList<String> voto: listaVoti){
                votostmt = conn.prepareStatement(votoSql);
                votostmt.setInt(1,Integer.parseInt(voto.get(0)));
                System.out.println(numeroGiornta);
                votostmt.setInt(2,numeroGiornta);
                //voto
                votostmt.setFloat(3,Float.valueOf(voto.get(1)));
                //gol fatto
                votostmt.setInt(4, Integer.parseInt(voto.get(2)));
                //gol subito
                votostmt.setInt(5,Integer.parseInt(voto.get(3)));
                //autogol
                votostmt.setInt(6,Integer.parseInt(voto.get(7)));
                //rigore fatto
                votostmt.setInt(7,Integer.parseInt(voto.get(6)));
                //rigore subito
                votostmt.setInt(8,Integer.parseInt(voto.get(5)));
                //rigore parato
                votostmt.setInt(9,Integer.parseInt(voto.get(4)));
                //assist
                votostmt.setInt(10,Integer.parseInt(voto.get(10)));
                //ammonizione
                votostmt.setInt(11,Integer.parseInt(voto.get(8)));
                //espulsione
                votostmt.setInt(12,Integer.parseInt(voto.get(9)));
                //assist da fermo
                votostmt.setInt(13,Integer.parseInt(voto.get(11)));

                rs = votostmt.executeUpdate();
            }

            return (rs==1);

        }catch(SQLException se){
            se.printStackTrace();
            return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;

        }finally {
            try { conn.close(); } catch (Exception e) {  }
        }

    }

    public int deleteCampionati(){
        Connection conn = null;
        PreparedStatement deletestmt = null;
        String[] deleteSql = {"DELETE FROM Campionato","delete from  Iscrizione","delete from Fantasquadra","delete from Classifica","delete from Regolamento","delete from Partita","delete from Giornata"};

        int rs  =0;
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            int i =0;

            for(String delete : deleteSql){
                deletestmt = conn.prepareStatement(delete);
                deletestmt.execute();
                System.out.println(i);
                i++;
            }

            return rs;

        }catch(SQLException se){
            se.printStackTrace();
            return rs;

        }catch(Exception e){
            e.printStackTrace();
            return rs;

        }finally {
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }
    }
}

