/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentalWookieYodels;

import Messages.NetworkMessage;

/**
 *
 * @author woodi
 */
public interface Codec {
    public String getDelimiter();
    public String getHeader();
    public String getFooter();
    public String getObjectIdentifier();
    public String encode(NetworkMessage message);
    public String encode(String string);
    public String decode(String string);
    public NetworkMessage[] parse(String string);
}