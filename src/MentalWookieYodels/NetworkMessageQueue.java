/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentalWookieYodels;

import Messages.NetworkMessage;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author woodi
 */
public class NetworkMessageQueue {
    
    ConcurrentLinkedQueue<NetworkMessage> messages;
    
    public NetworkMessageQueue(){
        messages = new ConcurrentLinkedQueue();
    }
    
    public void add(NetworkMessage m){
        messages.add(m);
    }
    
    public void add(NetworkMessage[] ms){
        messages.addAll(Arrays.asList(ms));
    }
    
    public NetworkMessage getNextMessage(){
        return messages.poll();
    }
    
    public boolean isEmpty(){
        return this.messages.isEmpty();
    }
    
}
