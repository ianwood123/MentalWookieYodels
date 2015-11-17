/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentalWookieYodels;

import java.net.Socket;

/**
 *
 * @author woodi
 */
public abstract class NetworkHandler implements Runnable {
    Socket socket;
    NetworkMessageQueue queue;
    Codec codec;
    
    boolean shuttingDown = false;
    
    public NetworkHandler(Socket s, NetworkMessageQueue mq, Codec c){
        socket = s;
        queue = mq;
        codec = c;
    }
    
}
