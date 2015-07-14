package MentalWookieYodels;

import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

abstract public class MessageController{
    
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
    
    public MessageController(Socket s, MessageQueue q) throws SocketException{
                
        socket = s;
        s.setSendBufferSize(1000);
        s.setReceiveBufferSize(1000);
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
    
    public void setRunnable(Runnable r){
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
    public void setSocketBufferSizes(int send, int receive) throws SocketException{
        socket.setSendBufferSize(send);
        socket.setReceiveBufferSize(receive);
    
    }
}
