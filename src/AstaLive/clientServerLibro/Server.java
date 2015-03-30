package AstaLive.clientServerLibro;

/**
 * Created by alessandro on 30/03/15.
 */
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server  extends  JFrame{
    private JTextField enterField;
    private JTextArea displayArea;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    private int counter = 1;

    public Server(){
        super("Server");

        Container container = getContentPane();

        enterField = new JTextField();
        enterField.setEditable(false);
        enterField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendData(e.getActionCommand());
                enterField.setText("");
            }
        });

        container.add(enterField, BorderLayout.NORTH);

        displayArea = new JTextArea();
        container.add(new JScrollPane(displayArea),BorderLayout.CENTER);

        setSize(300,150);
        setVisible(true);
    }

    public void runServer(){
        try{
            server = new ServerSocket(12345,100);

            while (true){
                try{
                    waitForConnection();
                    getStreams();

                    processConnection();
                }
                catch (EOFException eofe){
                    System.out.println("Server teminated connection.");
                }
                finally {
                    closeConnection();
                    ++counter;
                }
            }
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException{
        displayMessage("Waiting for connection\n");
        connection = server.accept();
        displayMessage("Connection "+counter+" received from: "+ connection.getInetAddress().getHostName());

    }

    private void getStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());

        displayMessage("\n Got I/O streams\n");
    }

    private void processConnection() throws IOException{
        String message = "Connection succesful";
        sendData(message);

        setTextFieldEditable(true);

        do{
            try{
                message = (String) input.readObject();
                displayMessage("\n"+message);
            }
            catch (ClassNotFoundException ce){
                displayMessage("\n Unknown object type received");
            }
        } while(!message.equals("CLIENT>>> TERMINATE"));
    }

    private void closeConnection(){
        displayMessage("\n Terminating connection \n");
        setTextFieldEditable(false);

        try{
            output.close();
            input.close();
            connection.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void sendData(String message){
        try{
            output.writeObject("SERVER>>> " + message);
            output.flush();
            displayMessage("\nSERVER>>> " + message);
        } catch (IOException ioe){
            displayArea.append("\nError writing object");
        }
    }

    private void displayMessage(final String messageToDisplay){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                displayArea.append(messageToDisplay);
                displayArea.setCaretPosition(displayArea.getText().length());
            }
        });
    }

    private void setTextFieldEditable(final boolean editable){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                enterField.setEditable(editable);
            }
        });
    }

    public static void main(String args[]){
        Server application = new Server();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.runServer();
    }
}
