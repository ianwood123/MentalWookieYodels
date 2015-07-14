package MentalWookieYodels;

import java.util.ArrayList;

abstract public class MessageQueue{

    ArrayList<Message> messages;
    //holds message objects. 
    
    MessageQueue(){
        messages = new ArrayList();
    }

    public void push(Message m){
        messages.add(m);
        onPush();
    }
    //pushes a message onto the top of the stack, then notifies listener
    
    public void shift(Message m){
        messages.remove(m);
        onShift();
    }
    //removes a specific message from the stack, then notifies listener

    public void shift(){
        messages.remove(0);
        onShift();
    }
    //removes the first message in the stack, then notifies listener

    public Message getNextMessage(){
        return messages.get(0);
    }
    //simply gets the next message.  no change is made to the arraylist
    
    public boolean isEmpty(){
        return this.messages.isEmpty();
    }

    abstract public void onPush();

    abstract public void onShift();
    //requires coder to use these
}



    
