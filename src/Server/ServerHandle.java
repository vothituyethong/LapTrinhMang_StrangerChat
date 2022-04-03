package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ServerHandle extends Thread {

    public ArrayList<String> waitingQueue = new ArrayList<>();
    private Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private String name = null;
    private String partner = "";
    private int accept = -1;
    public Server server;

    public ServerHandle(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println(socket);
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void send(String message) throws IOException {
        System.out.println("Send: "+message);
        out.write(message);
        out.newLine();
        out.flush();
    }

    private void removeWorker(ServerHandle worker) {
        synchronized (this) {
            server.workers.remove(worker);
            worker.close();
        }
    }
   
    private void sendToPartner(ServerHandle from, String message) {
        synchronized (this) {
            System.out.println(from.name+": "+message);
            for (int i = 0; i < server.workers.size(); i++) {
                ServerHandle worker = server.workers.get(i);                
                if(worker.name.equals(this.partner)){
                    try {
                        worker.send(message);
                    } catch (IOException e) {
                        server.workers.remove(i--);
                        worker.close();
                    }
                }    
            }
        }
    }
    
    public void setWaitingQueue(){
        for (int i = 0; i < server.workers.size(); i++){
            ServerHandle worker = server.workers.get(i);
            if(worker.partner.equals("") && !worker.name.equals(this.name))
                waitingQueue.add(worker.name);
        }
    }
    
    public void setWaitingQueueExcept(String prePartnerName){
        for (int i = 0; i < server.workers.size(); i++){
            ServerHandle worker = server.workers.get(i);
            if(worker.partner.equals("") && !worker.name.equals(this.name)
                    &&!worker.name.equals(prePartnerName))
                waitingQueue.add(worker.name);
        }
    }
    
    public void askToChat(){
        if(server.workers.size()>1){
            int flag = 0;            
            while(waitingQueue.size()>0 && flag==0){
                for (int i = 0; i < server.workers.size(); i++){
                    ServerHandle worker = server.workers.get(i);
                    String randomPartner = randomPartner();
                    if(worker.name.equals(randomPartner)){
                        try {
                            this.send("SASK1_"+worker.name);
                            String ms1 = in.readLine();        
                            if(ms1.equals("SREP1_yes"))
                                this.accept = 1;
                            if(ms1.equals("SREP1_no"))
                                this.accept = 0;
                            if(this.accept == 0 )
                                waitingQueue.remove(randomPartner);
                            if(this.accept == 1 ){
                                worker.send("SASK2_"+this.name);                            
                                while(worker.accept == -1){
                                    System.out.println(worker.accept);
                                }                            
                                if(worker.accept == 0)
                                    waitingQueue.remove(randomPartner);
                                if(worker.accept == 1){
                                    this.partner = worker.name;
                                    worker.partner = this.name;
                                    this.send("SSTART_CHAT_"+worker.name);
                                    worker.send("SSTART_CHAT_"+this.name);                                
                                    flag++;
                                }
                            }
                            this.accept = -1;
                        } catch (IOException ex) {                            
                        }
                        break;
                    }  
                }
            }
        }
    }
    
    public void requireToClose(){
        for (int i = 0; i < server.workers.size(); i++){
            ServerHandle worker = server.workers.get(i);
            if(worker.name.equals(this.partner)){
                try {
                    worker.send("CLOSE_WINDOW");
                } catch (IOException ex) {                    
                }
                worker.accept = -1;
                worker.partner = "";
                this.partner = "";
            }     
        }    
    }
    
    public String randomPartner(){
        int random = new Random().nextInt(waitingQueue.size());
        return waitingQueue.get(random);
    }
    
    public int isExistName (String name){
        for (int i = 0; i < server.workers.size(); i++) {
            ServerHandle worker = server.workers.get(i);
            if(worker.name.toLowerCase().equals(name.toLowerCase()))
                return 1;
        }
        return 0;
    }
        
    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readLine();
                System.out.println("Receive: "+message);
                switch(message){
                    case "SREP2_yes":{
                        this.accept = 1;
                        break;
                    }
                    case "SREP2_no":{
                        this.accept = 0;
                        break;
                    }
                    case "CLOSE_WINDOW":{
                        this.accept = -1;
                        setWaitingQueueExcept(partner);
                        requireToClose();                                           
                        askToChat();
                        break;
                    }
                    default:{
                        if (name == null){
                            name = message;
                            
                            if(isExistName(name)==0){
                                server.workers.add(this);
                                System.out.println("name: "+name);
                                setWaitingQueue();
                                askToChat();
                            }
                            else{
                                this.send("S_Exist");
                                name = null;
                            }   
                        }
                        else
                            sendToPartner(this, message);
                    } 
                }
            }
        } catch (IOException e) {
        }
        removeWorker(this);
    }

    private void close() {
        try {
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
        }
    }
}