/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import java.util.ArrayList;

import MentalWookieYodels.KeyValue;

/**
 *
 * @author woodi
 */
public class NetworkMessage{
    public final ArrayList<KeyValue> contents;
    
    public NetworkMessage(){
        this.contents = new ArrayList();
    }
    
    public NetworkMessage(ArrayList<KeyValue> c){
    contents =  new ArrayList(c);
    }
}

