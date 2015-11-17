/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentalWookieYodels;

import Messages.NetworkMessage;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author woodi
 */
class Receiver extends NetworkHandler{

    Scanner in;
    
    
    
    public Receiver(Socket s, NetworkMessageQueue mq, Codec c) {
        super(s, mq, c);
        
        try {
            in = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        in.useDelimiter(codec.getHeader() + "|" + codec.getFooter());
    }

    @Override
    public void run() {
        while(!shuttingDown){
            NetworkMessage[] m;
            String fromReceiver = codec.decode(in.next());  
            m = codec.parse(fromReceiver);
            this.queue.add(m);
        }
    }
}

