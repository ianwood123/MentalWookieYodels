/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentalWookieYodels;

import Messages.DefaultMessageFactory;
import Messages.NetworkMessage;
import java.util.ArrayList;

/**
 *
 * @author woodi
 */
public class MWY2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        
        DefaultMessageFactory dmf = new DefaultMessageFactory();
        CustomCodec c = new CustomCodec();
        NetworkMessageQueue incoming, outgoing;
        incoming = new NetworkMessageQueue();
        outgoing = new NetworkMessageQueue();
        NetworkController nc = new NetworkController("localhost", 8080, c, incoming, outgoing);

        
        KeyValue login = new KeyValue("class","LoginMessage");
        KeyValue username = new KeyValue("username" , "bob");
        KeyValue password = new KeyValue("password", "1234");
        KeyValue endoflogin = new KeyValue("end","LoginMessage");
        ArrayList<KeyValue> stuff = new ArrayList();
        stuff.add(login);
        stuff.add(username);
        stuff.add(password);
        stuff.add(endoflogin);
        NetworkMessage r = new NetworkMessage(stuff);
        outgoing.add(r);
    }
}
