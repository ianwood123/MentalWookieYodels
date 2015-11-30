/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentalWookieYodels;

import Messages.Message;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author woodi
 */
public class MessageQueue {
    
    ConcurrentLinkedQueue<Message> messages;
    
    public MessageQueue(MessageQueue mq){
        this.messages = mq.messages;
    }
    
    public MessageQueue(){
        messages = new ConcurrentLinkedQueue();
    }
    
    public void add(Message m){
        messages.add(m);
    }
    
    public void add(Message[] ms){
        messages.addAll(Arrays.asList(ms));
    }
    
    public Message getNextMessage(){
        return messages.poll();
    }
    
    public boolean isEmpty(){
        return this.messages.isEmpty();
    }
    
}
