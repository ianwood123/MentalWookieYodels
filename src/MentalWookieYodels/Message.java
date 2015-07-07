package MentalWookieYodels;

/**
 *
 * @author woodi
 */
public class Message{
        

    int id;
    //the id of the message. this isn't really useful currently
    String messageContents;
    //the message contents. this contains what is being sent over network. 

    public Message(int identity, String msg){
        id = identity;
        messageContents = msg;

    }
    //This is a basic message class. I may choose to update this later, but for 
    //the current needs this is sufficient. 
    //as it stands, this is currently a wrapper for a string. blah. 
}