/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentalWookieYodels;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author woodi
 */
public class MessageReceiver extends MessageController{

    
    Scanner in;
    //scanner allows for reading text and strings from inputstreams
    //this is used to receive data over a socket (network)
    
    public MessageReceiver(Socket s, MessageQueue q) throws IOException{
        super(s,q);
        //superclass constructor first
        
        in = new Scanner(socket.getInputStream());
        
        //initializes the scanner from the sockets input stream. this
        //allows us to change text we get over the network into messages for
        //interpretation in the application
    }
    
    public void useCodecDelimiter(Codec c){
        in.useDelimiter(c.getCodecDelimiter());
    }
    
    public Scanner getScanner(){
        return this.in;
    }
    
    public String getNextLine(){
        return in.nextLine();
    }
    

}
