package MentalWookieYodels;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

//Oh boy, the fun stuff.

abstract public class NetworkController {
    
    MessageSender ms;
    //sends messages outside of the network. 
    
    MessageReceiver mr;
    //receives messages from the network.
    
    final MessageQueue outboundMessages;
    //a message queue that holds and operates on messages | these go out
    
    final MessageQueue inboundMessages;
    //a message queue that holds and operates on messages | these go in

    
    final Socket socket;
    //The socket we are going to use. 
    
    final Runnable sendThread;
    //this contains the code we will run when a message is put into the inboundQueue
    
    final Runnable receiveThread;
    //this contains the code we will run when a message is put into the outboundQueue

    public void addMessage(String x){
        Message m = new Message (10,x);
        outboundMessages.push(m);
    }
    //test code. may be implemented, but will definitely be different.
    
    public NetworkController(String ip, int port) throws IOException{
        
        
        outboundMessages = new MessageQueue() {

            @Override
            public void onPush() {

            }

            @Override
            public void onShift() {

            }
        };
        //instantiates queue and defines how it acts when updated.
        
        inboundMessages = new MessageQueue() {

            @Override
            public void onPush() {
                
            }

            @Override
            public void onShift() {
                
            }
        };
        //instantiates queue and defines how it acts when updated.
        
        socket = new Socket(ip, port);
        //connects to ipaddress using port.
        
        socket.setKeepAlive(true);
        //keeps connection alive
        
        ms = new MessageSender(socket, outboundMessages);
        // passes the proper queue and socket to the sender.
        
        mr = new MessageReceiver(socket, inboundMessages);
        //passes the proper queue and socket to the receiver
        
        sendThread = () -> {
            this.outboundThreadBody();
        };
        //defines what runs in the thread that handles output to server
        
        receiveThread = () -> {
            inboundThreadBody();
        };
        //defines what runs in the thread that handles input from server
        
        ms.setThreadRunnable(sendThread);
        mr.setThreadRunnable(receiveThread);
        //required to prevent instantiation issues. all three of these components
        //talk to eachother, and this allows them to do so without issue.
        //this sets the threads in the controllers use the runnable defined later
        //than initialization of the actual controller.
        
    }
    
    public NetworkController() throws IOException{
        
        socket = null;
        outboundMessages = new MessageQueue() {

            @Override
            public void onPush() {
                runThreadOn(ms);
            }
            

            @Override
            public void onShift() {

            }
        };
        inboundMessages = new MessageQueue() {
            
            @Override
            public void onPush() {

            }

            @Override
            public void onShift() {
                runThreadOn(mr);
            }
        };
        
        ms = new MessageSender( outboundMessages);
        mr = new MessageReceiver( inboundMessages);
        
        sendThread = () -> {
            while(!ms.isEmpty()){
                ms.sendNextMessage();
            }
        };
        
        receiveThread = () -> {
        };
        
        ms.setThreadRunnable(sendThread);
        mr.setThreadRunnable(receiveThread);
    }//test code

    public void setKeepAlive(boolean b) throws SocketException{
        socket.setKeepAlive(b);
    }
    //setkeepalive wrapper
    
    
    private void runThreadOn(MessageController mc){
        mc.runThread();
    }
    //runs the thread, which simply calls .execute on an ExecutorService
    //in the controller

    abstract public void onOutboundMessageQueueUpdate();

    abstract public void onInboundMessageQueueUpdate();

    abstract public void outboundThreadBody();

    abstract public void inboundThreadBody();
    //forces coder to implement these methods.
    //these method names are - I hope - self-explanatory.
    //they handle what to do when an 'eventt is fired. 
}



