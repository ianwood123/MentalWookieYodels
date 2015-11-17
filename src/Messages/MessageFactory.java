/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import Exceptions.MessageException;
import Exceptions.MissingClassReferenceException;
import Exceptions.NonExistentFieldException;

/**
 *
 * @author woodi
 */
public interface MessageFactory {
    
    Message createMessage(NetworkMessage m) throws MessageException, MissingClassReferenceException;
    NetworkMessage createNetworkMessage(Message m) throws MessageException, NonExistentFieldException;
    Class getClassByMessage(NetworkMessage m) throws MissingClassReferenceException;
}
