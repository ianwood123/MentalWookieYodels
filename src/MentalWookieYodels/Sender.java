/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentalWookieYodels;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author woodi
 */
class Sender extends NetworkHandler{

    PrintWriter out;
    

    
    public Sender(Socket s, NetworkMessageQueue mq, Codec c) {
        super(s, mq, c);
        try {
            this.out = new PrintWriter(socket.getOutputStream(), false);
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while(!shuttingDown){
            if(this.queue.isEmpty()){
                out.print(codec.getHeader() + codec.getFooter());
                out.flush(); //basically a heartbeat to let the server know we're still here. 
            }
            else{
                String s = codec.encode(this.queue.getNextMessage());
                out.print(s);
                out.flush();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
