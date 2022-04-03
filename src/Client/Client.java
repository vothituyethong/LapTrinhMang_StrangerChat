package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.StringUtils;

public class Client extends Thread {
    Socket socket;
    BufferedWriter out;
    BufferedReader in ;
    String dataReceive = "" ;
    public String name = "";
    public String partner = "";
    public frm_EnterName frmEnterName;
    public frm_Chat frmChat;
    public frm_Waiting frmWaiting;
    
    public Client(String serverAddress, int serverPort) throws UnknownHostException, IOException {
        socket = new Socket(InetAddress.getByName(serverAddress), serverPort);
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    public void send(String message) throws IOException {
        if(name.equals(""))
            name = message;
        System.out.println("Send: "+message);
        out.write(message);
        out.newLine();
        out.flush();
    }
    
    public void closeWindow(){
        frmWaiting.setVisible(true);
        frmChat.resetChatBox();
        partner = "";
        if(frmChat.number == 1){
            try {
            send("CLOSE_WINDOW");
            } catch (IOException ex) {  
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                dataReceive = in.readLine();
                System.out.println("Receive: "+dataReceive);
                if(dataReceive.equals("S_Exist")){
                    frmEnterName.setDataReceive(dataReceive);
                    name = "";
                }                
                if(dataReceive.contains("SASK1_")){
                    String other = StringUtils.substringAfter(dataReceive, "SASK1_");  
                    int choice = JOptionPane.showConfirmDialog(null,name+
                            ", Do you want to chat with "+other+"?", "",JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION)
                        send("SREP1_yes");
                    else
                        send("SREP1_no");
                }
                if(dataReceive.contains("SASK2_")){
                    String other = StringUtils.substringAfter(dataReceive, "SASK2_");                   
                    int choice = JOptionPane.showConfirmDialog(null,name+
                            ", Do you want to chat with "+other+"?", "",JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION)
                        send("SREP2_yes");
                    else
                        send("SREP2_no");
                }
                if(dataReceive.contains("SSTART_CHAT_")){
                    partner = StringUtils.substringAfter(dataReceive, "SSTART_CHAT_");
                    frmChat.setVisible(true);
                    frmChat.setTxtPartner(partner);
                    frmWaiting.dispose();
                }
                if(dataReceive.contains("MES_")){
                    dataReceive = StringUtils.substringAfter(dataReceive, "MES_");
                    frmChat.appendChatBox(partner+": "+dataReceive);
                }
                if(dataReceive.equals("CLOSE_WINDOW")){
                    frmChat.number = 2;
                    frmChat.dispose();
                    frmChat.resetChatBox();
                    frmWaiting.setVisible(true);
                }  
            }
        } catch (IOException e) {
        }
        close();
        System.exit(0);
    }

    private void close() {
        try {
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Client thisClient = null;
        try {
            thisClient = new Client("localhost", 1402);
            thisClient.start();
            thisClient.frmEnterName = new frm_EnterName();
            thisClient.frmEnterName.client = thisClient;
            thisClient.frmChat = new frm_Chat();
            thisClient.frmChat.client = thisClient;
            thisClient.frmEnterName.setVisible(true);
            thisClient.frmWaiting = new frm_Waiting();
            thisClient.frmWaiting.client = thisClient;
            
            while (true) 
                scanner.nextLine();
            
        } catch (IOException e) {
        }
        if (thisClient != null)
            thisClient.close();
        scanner.close();    
    }    
}