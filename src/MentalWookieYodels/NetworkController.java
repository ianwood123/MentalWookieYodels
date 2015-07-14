package MentalWookieYodels;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

//Oh boy, the fun stuff.

abstract public class NetworkController {
    
    private MessageSender ms;
    //sends messages outside of the network. 
    
    private MessageReceiver mr;
    //receives messages from the network.
    
    private final MessageQueue outboundMessages;
    //a message queue that holds and operates on messages | these go out
    
    private final MessageQueue inboundMessages;
    //a message queue that holds and operates on messages | these go in

    
    final Socket socket;
    //The socket we are going to use. 
    
    Runnable sendThread;
    //this contains the code we will run when a message is put into the inboundQueue
    
    Runnable receiveThread;
    //this contains the code we will run when a message is put into the outboundQueue
    Codec codec;
    
    public NetworkController(String ip, int port) throws IOException{
        
        
        outboundMessages = new MessageQueue() {

            @Override
            public void onPush() {
                onOutboundMessageQPush();
            }

            @Override
            public void onShift() {
                onOutboundMessageQShift();
            }
        };
        //instantiates queue and defines how it acts when updated.
        
        inboundMessages = new MessageQueue() {

            @Override
            public void onPush() {
                onInboundMessageQPush();
            }

            @Override
            public void onShift() {
                onInboundMessageQShift();
            }
        };
        //instantiates queue and defines how it acts when updated.
        
        socket = new Socket(ip, port);
        //connects to ipaddress using port.
        
        socket.setKeepAlive(true);
        //keeps connection alive
        
        ms = new MessageSender(socket, getOutboundMessages());
        // passes the proper queue and socket to the sender.
        
        mr = new MessageReceiver(socket, getInboundMessages());
        //passes the proper queue and socket to the receiver


        //required to prevent instantiation issues. all three of these components
        //talk to eachother, and this allows them to do so without issue.
        //this sets the threads in the controllers use the runnable defined later
        //than initialization of the actual controller.
        
    }

    public void setKeepAlive(boolean b) throws SocketException{
        socket.setKeepAlive(b);
    }
    //setkeepalive wrapper
    
    
    private void runThreadOn(MessageController mc){
        mc.runThread();
    }
    //runs the thread, which simply calls .execute on an ExecutorService
    //in the controller

    public void setRunnables(Runnable in, Runnable out){
    
        this.receiveThread = in;
        this.sendThread = out;
        ms.setRunnable(sendThread);
        mr.setRunnable(receiveThread);
    }
    
    abstract public void onOutboundMessageQPush();
    abstract public void onOutboundMessageQShift();
    abstract public void onInboundMessageQPush();
    abstract public void onInboundMessageQShift();
    //forces coder to implement these methods.
    //these method names are - I hope - self-explanatory.
    //they handle what to do when an 'event' is fired. 

    public MessageSender getMessageSender() {
        return ms;
    }

    public MessageReceiver getMessageReceiver() {
        return mr;
    }

    public MessageQueue getOutboundMessages() {
        return outboundMessages;
    }

    public MessageQueue getInboundMessages() {
        return inboundMessages;
    }
    public void setCodec(Codec c){
        this.codec = c;
    }
}
