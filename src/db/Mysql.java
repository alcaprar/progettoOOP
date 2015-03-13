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
                System.out.print(rs.getString("Nome"));
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

    public void registraTrue(Registra registraForm){
        Object[] options = {"OK"};
        int succesDialog = JOptionPane.showOptionDialog(registraForm.getContentPane(), "Registrazione effettuata con successo!",
                "Risposta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if(succesDialog==0 || succesDialog==-1) registraForm.dispose();
    }


    public void registraFalse(Registra registraForm){
        Object[] options = {"OK"};
        int succesDialog = JOptionPane.showOptionDialog(registraForm.getContentPane(), "Nickname già registrato. Provare con un'altro.",
                "Nickname esistente",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

    }
}