package MentalWookieYodels;

import java.util.ArrayList;

public interface Codec {

    public String getCodecDelimiter();
    
    public String encodeAll(ArrayList<Message> msgs);

    public String encode(Message msg);

    public ArrayList<Message> decodeAll(String str);

    public String singularDecode(String str);

}
