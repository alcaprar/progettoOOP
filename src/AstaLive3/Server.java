package AstaLive3;

import AstaLive.Client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by alessandro on 02/04/15.
 */
public class Server extends Thread {
    private ServerSocket Server;
    private boolean continua = true;
    private ArrayList<ClientConnected> listaConnesi;

    private ServerGUI serverGUI;

    private int numPart=2;

    private int IDunici=1;


    public Server(int porta, ServerGUI gui){
        serverGUI=gui;
        listaConnesi = new ArrayList<ClientConnected>();
        try{
            Server = new ServerSocket(porta);
            System.out.println("Server in attesa sulla porta " + porta);
            serverGUI.appendConsole("Server in attesa sulla porta " + porta);
            this.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
            while (continua) {
                try {
                    serverGUI.appendConsole("In attesa di connessioni.");
                    Socket client = Server.accept();
                    serverGUI.appendConsole("Connessione accettata da: " + client.getInetAddress());
                    ClientConnected clientC = new ClientConnected(client,IDunici++);
                    listaConnesi.add(clientC);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    }

    class ClientConnected extends Thread{
        private Socket client =null;
        private BufferedReader in=null;
        private PrintStream out=null;

        private int id;
        private String username;

        public ClientConnected(){
        }


        public ClientConnected(Socket clientSocket, int ID){
            this.client=clientSocket;
            this.id =ID;
            //apre i canali di I/O
            try{
                in = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));
                out = new PrintStream(client.getOutputStream(), true);
            }catch (Exception e){
                e.printStackTrace();
            }
            this.start();
        }

        public void run(){
            while (true){
                try{
                    serverGUI.appendConsole(in.readLine());
                    System.out.println(in.readLine());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
}
