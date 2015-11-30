/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentalWookieYodels;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author woodi
 */
public class NetworkController {
    
    Socket socket;
    
    Sender sender;
    
    Receiver receiver;
    
    ExecutorService exe;
    
    Codec codec;
    
    String ipAddress;
    
    int port;
    
    NetworkMessageQueue in;
    NetworkMessageQueue out;
    
    private NetworkController(){}
    
    public NetworkController(String ip, int port, Codec c, NetworkMessageQueue incoming, NetworkMessageQueue outgoing){
        this.ipAddress = ip;
        this.port = port;
        this.codec = c;
        this.exe = Executors.newCachedThreadPool();
        in = incoming;
        out = outgoing;
    }
    
    public boolean checkConnection(){
        return socket.isConnected();
    }
    public boolean disconnect(){
        sender.shuttingDown = true;
        receiver.shuttingDown = true;
        exe.shutdown();
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(NetworkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return socket.isClosed() && exe.isShutdown();
        
    }
    
    public boolean connect(){  
        try {
            socket = new Socket(ipAddress,port);
            socket.setKeepAlive(true);
        } catch (IOException ex) {
            Logger.getLogger(NetworkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        this.sender = new Sender(socket, out, codec);
        this.receiver = new Receiver(socket, in, codec);
        this.exe.execute(sender);
        this.exe.execute(receiver);
        
        return checkConnection();   
    }

}
