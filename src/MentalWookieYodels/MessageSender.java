/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentalWookieYodels;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 *
 * @author woodi
 */

public class MessageSender extends MessageController{

    PrintWriter out;
    //PrintWriter allows for sending strings out to another computer
    //when used on a socket object. 
    //This is the declaration.
    
    public MessageSender(Socket s, MessageQueue q) throws IOException{
        super(s,q);
        //does the superclass constructor first

        out = new PrintWriter(socket.getOutputStream(), false);
        //sets the printwriter to be the output stream of the socket
        //this allows us to send text to another computer
        exe = Executors.newCachedThreadPool();
        //executor service
    }
    
    public boolean isEmpty(){
       return queue.messages.isEmpty();
    }
    //public method to check if the queue is empty.
    
    public void sendNextMessage(){
        out.print(queue.getNextMessage().createMessage());

        out.flush();
        queue.shift();
    }
    //sends the next message in the queue, then removes the message from the queue
    
    public PrintWriter getPrintWriter(){
        return this.out;
    }
    @Override
    public void runThread(){
        this.exe.execute(runnable);
    }
}