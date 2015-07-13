package MentalWookieYodels;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

abstract class MessageController{
    
    Socket socket;
    //declares a socket to use for this controller
    
    MessageQueue queue;
    //declares a MessageQueue to use for this controller
    
    ExecutorService exe;
    //ExecutorService is used later for a cached thread pool.
    //This is used to run code when a Message is added (or removed) from the
    //MessageQueue. 
    
    Runnable runnable;
    //This is the runnable that will be run when the MessageController is notified
    //of some action. 
    
    Codec codec;
    
    MessageController(Socket s, MessageQueue q){
                
        socket = s;
        //sets socket from NetworkController
        //this is important, because it references the same object
        
        queue = q;
        //sets MessageQueue from NetworkController
        //this is important, because it references the same object
        
        exe = Executors.newCachedThreadPool();
        //executor for creating and running threads
        
    }
    //here, we have the constructor of the MessageController

    
    public void setCodec(Codec c){
        codec = c;
    }
    
    public void setThreadRunnable(Runnable r){
        runnable = r;
    }
    //sets the runnable for this MessageController object. This is required 
    //to prevent an initialization issue in the constructor of the NetworkController
    //This should be called after MessageController, MessageQueue, and the Runnables
    //are all initialized and set. 
    
    public void runThread(){
        exe.execute(runnable);
    }
    //executes the runnable for this MessageController object
    //this adds the runnable as a task to the thread pool. generally speaking, 
    //this runs the command immediately, in another thread. 
    
}


class MessageSender extends MessageController{

    PrintWriter out;
    //PrintWriter allows for sending strings out to another computer
    //when used on a socket object. 
    //This is the declaration.
    
    MessageSender(Socket s, MessageQueue q) throws IOException{
        super(s,q);
        //does the superclass constructor first

        out = new PrintWriter(socket.getOutputStream(), true);
        //sets the printwriter to be the output stream of the socket
        //this allows us to send text to another computer
       
    }
    
    public boolean isEmpty(){
       return queue.messages.isEmpty();
    }
    //public method to check if the queue is empty.
    
    public void sendNextMessage(){
        out.println(queue.getNextMessage().messageContents);
        queue.shift();
        
    }
    //sends the next message in the queue, then removes the message from the queue
    
}


class MessageReceiver extends MessageController{

    
    Scanner in;
    //scanner allows for reading text and strings from inputstreams
    //this is used to receive data over a socket (network)
    
    MessageReceiver(Socket s, MessageQueue q) throws IOException{
        super(s,q);
        //superclass constructor first
        
        in = new Scanner(socket.getInputStream());
        in.useDelimiter(codec.getCodecDelimiter());
        //initializes the scanner from the sockets input stream. this
        //allows us to change text we get over the network into messages for
        //interpretation in the application
    }
    
    public String getNextLine(){
        return in.nextLine();
    }
    

}
