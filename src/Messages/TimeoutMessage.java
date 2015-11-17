/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

/**
 *
 * @author woodi
 */
public class TimeoutMessage extends Message{

    public Long time;
        
    public TimeoutMessage(Long systemTime){
        super();
        time = systemTime;
    }    

    public TimeoutMessage() {
    }
    
    public Long getTime(){
    return time;
    }
}
