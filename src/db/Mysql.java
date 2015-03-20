package db;


import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

import classi.*;
import classi.Classifica;
import interfacce.*;

public class Mysql{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://db4free.net/progogg";
    static final String USER = "progogg";
    static final String PASS = "pagliarecci";

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
                utente.setEmail(rs.getString("Email"));;
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
            registra.setString(3,utente.getEmail());
            registra.setString(4,utente.getNome());
            registra.setString(5,utente.getCognome());
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

    public ArrayList<Squadra> selectSquadre(Persona utente){
        Connection conn = null;
        PreparedStatement squadrestmt = null;
        String squadreSql ="SELECT * from Iscrizione JOIN Fantasquadra on Iscrizione.IDsq=Fantasquadra.ID JOIN Campionato on Iscrizione.Campionato = Campionato.Nome JOIN Regolamento on Campionato.Nome=Regolamento.NomeCampionato where NickUt=?";

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
                Campionato campionato = new Campionato(rs.getString("Campionato"),rs.getInt("NrPartecipanti"),rs.getBoolean("Asta"),rs.getInt("GiornataInizio"),rs.getInt("GiornataFine"),rs.getInt("CreditiIniziali"),rs.getInt("OrarioConsegna"),rs.getInt("PrimaFascia"),rs.getInt("LargFascia"),rs.getInt("BonusCasa"),new Persona(rs.getString("Presidente")));
                Squadra squadra = new Squadra(rs.getInt("ID"),rs.getString("Nome"),utente,campionato);
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

    public ArrayList<GiornataReale> selectGiornate(){
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

    //crea il campionato
    public boolean creaCampionato(Campionato campionato){
        Connection conn = null ;
        PreparedStatement campionatostmt = null;
        String campionatoSql ="INSERT into Campionato value(?,?,?,?)";

        PreparedStatement regolamentstmt = null;
        String regolamentoSql = "INSERT into Regolamento value(?,?,?,?,?,?,?,?,?)";

        PreparedStatement iscrizionestmt = null;
        String iscrizioneSql = "INSERT into Fantasquadra(NickUt) value(?)";

        PreparedStatement partecipantistmt = null;
        String partecipantiSql = "INSERT into Iscrizione value(?,?,?)";

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


                for (int i = 0; i < campionato.getListaSquadrePartecipanti().size(); i++) {
                    //creo la nuova squadra
                    iscrizionestmt = conn.prepareStatement(iscrizioneSql, Statement.RETURN_GENERATED_KEYS);
                    iscrizionestmt.setString(1, campionato.getListaSquadrePartecipanti().get(i).getProprietario().getNickname());
                    iscrizionestmt.executeUpdate();
                    //trovo l'id della squadra appena inserita
                    ResultSet rs = iscrizionestmt.getGeneratedKeys();
                    rs.next();
                    int idIscrizione = rs.getInt(1);

                    //iscrivo la squadra appena creata
                    partecipantistmt = conn.prepareStatement(partecipantiSql);
                    partecipantistmt.setString(1, campionato.getNome());
                    partecipantistmt.setInt(2, campionato.getCreditiIniziali());
                    partecipantistmt.setInt(3, idIscrizione);
                    int rsPartecipanti = partecipantistmt.executeUpdate();
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
}

