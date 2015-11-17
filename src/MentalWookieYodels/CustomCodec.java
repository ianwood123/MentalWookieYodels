/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentalWookieYodels;

import Messages.NetworkMessage;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author woodi
 */

public class CustomCodec implements Codec{

    Codec child;
    
    public CustomCodec(Codec c){
        this.child = c;
    }
    public CustomCodec(){
        this.child = null;
    }
    
    @Override
    public String getDelimiter() {
        return "[;:]";
    }

    @Override
    public String getHeader() {
        return "begin:stream;";
    }
    
    @Override
    public String getFooter() {
        return "end:stream;";
    }
    
    @Override
    public String getObjectIdentifier(){
        
        return "begin";
    }
    
    public String getEndOfObjectIdentifier(){
        return "end";
    }
    
    private String keyValueSeparator(){
        return ":";
    }
    private String endOfPropertyIndicator(){
        return ";";
    }

    @Override
    public String encode(NetworkMessage message) {
        StringBuilder returnString = new StringBuilder("");
        returnString.append(this.getHeader());
        for(KeyValue kv :message.contents){
            if(kv.key.toLowerCase().equals("class")){
            returnString.append(getObjectIdentifier());
            }
            else{
            returnString.append(kv.key);
            }
            returnString.append(keyValueSeparator());
            returnString.append(kv.value);
            returnString.append(endOfPropertyIndicator());
        }
        returnString.append(getFooter());
        
        if(child != null){
            return child.encode(returnString.toString());
        }
        else{
            return returnString.toString();
        }
    }
    
    @Override
    public String encode(String string) {
        if(string.contains(getHeader()) && string.contains(getFooter())){
            return string;
        }
        else {
            return getHeader().concat(string).concat(getFooter());
        }
        
    }
    
    @Override
    public NetworkMessage[] parse(String string) {
        ArrayList<KeyValue> list = new ArrayList();
        ArrayList<NetworkMessage> msgs = new ArrayList();
        String in = decode(string);

        String[] keyvalues = in.split(getDelimiter());
        boolean needNewObject = false;
        KeyValue kv = new KeyValue();
        
        for(String s : keyvalues)
        {
            if(kv.key.isEmpty()){
                kv.key = s;
                
                if(kv.key.equals(this.getObjectIdentifier())){
                    kv.key = "class";
                }
                
                if(kv.key.equals(this.getEndOfObjectIdentifier())){
                    needNewObject = true;
                }
            }
            else{
                if (needNewObject == false){
                    kv.value = s;
                    list.add(kv);  
                    kv = new KeyValue();
                }
                else{
                    msgs.add(new NetworkMessage(list));
                    list.clear();
                    kv = new KeyValue();
                    needNewObject = false;
                }
                
            }
        }
        return msgs.toArray( new NetworkMessage[msgs.size()]);
    }

    @Override
    public String decode(String fromReceiver) {
        String s = fromReceiver;

        if(child != null){
            s = child.decode(s);
        }
        
        s = s.replaceAll(getHeader(), "");
        s = s.replaceAll(getFooter(), "");
        s = s.trim();
        
        return s;
    }    
}