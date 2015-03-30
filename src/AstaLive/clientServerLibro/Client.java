package AstaLive.clientServerLibro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by alessandro on 30/03/15.
 */
public class Client extends JFrame {
    private JTextField enterField;
    private JTextArea displayArea;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message="";
    private String chatServer;
    private Socket client;

    public Client(String host){
        super("Client");

        chatServer=host;

        Container container = getContentPane();

        enterField=new JTextField();
        enterField.setEditable(false);
        enterField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendData(e.getActionCommand());
                enterField.setText("");
            }
        });

        container.add(enterField, BorderLayout.NORTH);

        displayArea=new JTextArea();
        container.add(new JScrollPane(displayArea),BorderLayout.CENTER);

        setSize(300,150);
        setVisible(true);
    }

    private void runClient(){
        try{
            connectToServer();
            getStreams();
            processConnection();
        }
        catch (EOFException eofe){
            System.out.println("Client terminated connection");
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        finally {
                closeConnection();
        }
    }

    private void connectToServer() throws IOException{
        displayMessage("Attempting connection\n");

        client = new Socket(InetAddress.getByName(chatServer),12345);

        displayMessage("Connected to: "+client.getInetAddress().getHostName());
    }

    private void getStreams() throws IOException{
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();

        input=new ObjectInputStream(client.getInputStream());

        displayMessage("\n Got I/O stream\n");
    }

    private void processConnection() throws IOException{
        setTextFieldEditable(true);

        do{
            try{
                message = (String) input.readObject();
                displayMessage("\n"+message);
            }
            catch (ClassNotFoundException cne){
                displayMessage("\nUnknown obkect type received");
            }
        }while (!message.equals("SERVER>>> TERMINATE"));
    }

    private void closeConnection(){
        displayMessage("\nClosing connection");
        setTextFieldEditable(false);

        try{
            output.close();
            input.close();
            client.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void sendData(String message){
        try{
            output.writeObject("CLIENT>>> " + message);
            output.flush();
            displayMessage("\nCLIENT>>> " + message);
        }
        catch (IOException ioe){
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
        Client application;

        if(args.length==0){
            application=new Client("127.0.0.1");
        } else{
            application=new Client(args[0]);
        }

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.runClient();
    }
}
