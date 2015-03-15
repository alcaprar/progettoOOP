package db;

import javafx.scene.paint.Color;

import javax.swing.*;
import java.sql.*;

import classi.*;
import interfacce.*;

public class Mysql{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://db4free.net/progogg";
    static final String USER = "progogg";
    static final String PASS = "pagliarecci";

    public boolean login(Persona utente){
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
                addSquadre(utente);
                return true;
            }
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



    public boolean registra(Persona utente){
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
        }catch(SQLException se){
            if(se.getErrorCode()==1062){
                //registraFalse(registraForm);
            }
            System.out.print(se.getErrorCode());
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally {
            try { conn.close(); } catch (Exception e) { /* ignored */ }
        }

    }


    public void addSquadre(Persona utente){
        Connection conn=null ;
        PreparedStatement stmt = null ;
        String contaString = "SELECT count(*) from Fantasquadra where NickUt=?";
        String squadreString = "SELECT * from Fantasquadra where NickUt=?";
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.prepareStatement(contaString);
            stmt.setString(1,utente.getNickname());

            ResultSet rscount = stmt.executeQuery();
            rscount.next();
            int numeroSquadre = rscount.getInt("count(*)");
            utente.numeroSquadre(numeroSquadre);

            stmt = conn.prepareStatement(squadreString);
            stmt.setString(1,utente.getNickname());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                //System.out.println(rs.getString("Nome"));
            }

        }catch(SQLException se){
            se.printStackTrace();

        }catch(Exception e){
            e.printStackTrace();

        }finally {
            try { conn.close(); } catch (Exception e) {
            //ignored
            }
        }

    }

    public String[] selectUtenti(){
        Connection conn=null ;
        PreparedStatement stmt = null ;
        String contaString = "SELECT count(*) from Utente where TipoUtente='u'";
        String utentiString = "SELECT * from Utente where TipoUtente='u'";

        String[] utenti=null;
        try{
            //registra il JBCD driver
            Class.forName(JDBC_DRIVER);
            //apre la connessione
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.prepareStatement(contaString);

            ResultSet rscount = stmt.executeQuery();
            rscount.next();
            int numeroUtenti = rscount.getInt("count(*)");
            utenti = new String[numeroUtenti];

            stmt = conn.prepareStatement(utentiString);
            ResultSet rs = stmt.executeQuery();
            int i =0;
            while(rs.next()){
                utenti[i] = rs.getString("Nickname");
                i++;
            }
            return  utenti;

        }catch(SQLException se){
            se.printStackTrace();
            return utenti;

        }catch(Exception e){
            e.printStackTrace();
            return utenti;

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
            campionatostmt.setBoolean(4, campionato.isPubblico());
            campionatostmt.setString(5,campionato.getPresidente().getNickname());
            int rscampionato = campionatostmt.executeUpdate();

            //inserisco il regolamento
            regolamentstmt = conn.prepareStatement(regolamentoSql);
            regolamentstmt.setString(1,campionato.getNome());
            regolamentstmt.setInt(2,campionato.getGiornataInizio());
            regolamentstmt.setInt(3,campionato.getGiornataFine());
            regolamentstmt.setInt(4,campionato.getCreditiIniziali());
            regolamentstmt.setInt(5,campionato.getOrarioConsegna());
            regolamentstmt.setInt(6,campionato.getPrimaFascia());
            regolamentstmt.setInt(7,campionato.getLargFascia());
            regolamentstmt.setInt(8,campionato.getBonusCasa());
            regolamentstmt.setInt(9,0);
            int rsregolamento = regolamentstmt.executeUpdate();

            //se sono stati inseriti dei partecipanti li inserisco uno alla volta
            if(campionato.getPartecipanti()!=null){
                for(int i=0; i<campionato.getPartecipanti().length;i++) {
                    //creo la nuova squadra
                    iscrizionestmt = conn.prepareStatement(iscrizioneSql,Statement.RETURN_GENERATED_KEYS);
                    iscrizionestmt.setString(1, campionato.getPartecipanti()[i]);
                    iscrizionestmt.executeUpdate();
                    //trovo l'id della squadra appena inserita
                    ResultSet rs = iscrizionestmt.getGeneratedKeys();
                    rs.next();
                    int idIscrizione = rs.getInt(1);

                    //iscrivo la squadra appena creata
                    partecipantistmt = conn.prepareStatement(partecipantiSql);
                    partecipantistmt.setString(1,campionato.getNome());
                    partecipantistmt.setInt(2, campionato.getCreditiIniziali());
                    partecipantistmt.setInt(3,idIscrizione);
                    int rsPartecipanti = partecipantistmt.executeUpdate();
                }

            }

            if(rscampionato==1 && rsregolamento==1){
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
}