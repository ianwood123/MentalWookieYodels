package MentalWookieYodels;

/**
 *
 * @author woodi
 */
public abstract class Message{
        
    
    String messageContents;
    //the message contents. this contains what is being sent over network. 

    public Message(){
        messageContents = null;
    }
    
    public abstract String createMessage();

    //This is a basic message class. I may choose to update this later, but for 
    //the current needs this is sufficient. 
    //as it stands, this is currently a wrapper for a string. blah. 
}