/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import Exceptions.MessageException;
import Exceptions.MissingClassReferenceException;
import Exceptions.NonExistentFieldException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import MentalWookieYodels.KeyValue;

/**
 *
 * @author woodi
 */

public class DefaultMessageFactory implements MessageFactory {
    
    public DefaultMessageFactory(){
    
    }
    
    public Message createMessage(NetworkMessage m) throws Exceptions.MessageException, Exceptions.MissingClassReferenceException{
    
        
        
        Class c = getClassByMessage(m);
        Field[] declaredFields = c.getDeclaredFields();
                
        ArrayList<Class> params = new ArrayList();
        for(Field f : declaredFields){
            params.add(f.getType());
        }
        Class[] parameterClasses = params.toArray(new Class[params.size()]);

        Object[] parameterObjects = new Object[declaredFields.length];
        for(KeyValue kv : m.contents){
            for(int i = 0; i < declaredFields.length; i++){
                if(kv.key.equalsIgnoreCase(declaredFields[i].getName())){
                    try {
                        parameterObjects[i] = declaredFields[i].getType().getConstructor(String.class).newInstance(kv.value);
                    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(DefaultMessageFactory.class.getName()).log(Level.SEVERE, "Error in parameter creation/initialization from NetworkMessage: ", ex);
                            throw new MessageException("Something went wrong! Here's more information: ", ex);
                    }
                }
            }
        }

        Constructor construct;
        Object message;
        
        try {
            construct = c.getConstructor(parameterClasses);
            message = construct.newInstance(parameterObjects);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(DefaultMessageFactory.class.getName()).log(Level.SEVERE, "Error in Message constructor from NetworkMessage: ", ex);
            throw new MessageException("Something went wrong! Here's more information: ", ex);
        }

        return (Message) message;
    }
 
    
    
    
    
    public NetworkMessage createNetworkMessage(Message m) throws NonExistentFieldException{
        
        NetworkMessage result; 
        ArrayList<KeyValue> items = new ArrayList();
        Class clazz = m.getClass();
        items.add(new KeyValue("class",m.getClass().getSimpleName()));
        for(Field f : clazz.getFields()){
            try {
                items.add(new KeyValue(f.getName(), f.get(m).toString()));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(DefaultMessageFactory.class.getName()).log(Level.SEVERE, null, ex);
                throw new NonExistentFieldException("No field" + f.getName() + " found on object: " + m.getClass().toString(), ex);
            }
        }
        items.add(new KeyValue("end",m.getClass().getSimpleName()));
        
        result = new NetworkMessage(items);
        
        return result;
    }    
    
    public Class getClassByMessage(NetworkMessage m) throws MissingClassReferenceException{
        
        for(KeyValue kv : m.contents)
        {
           if(kv.key.toLowerCase().equals("class")){
               try {
                   return Class.forName("Messages." + kv.value);
               } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DefaultMessageFactory.class.getName()).log(Level.SEVERE, null, ex);
                    throw new Exceptions.MissingClassReferenceException("This class does not exist in the current context: " + kv.value, ex);
               }
           }
        }
        return m.getClass();
    }
}

