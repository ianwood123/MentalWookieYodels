package Exceptions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author woodi
 */
public class ServerNotFoundException extends Exception{
        public ServerNotFoundException(String stuff, java.lang.Exception ex) {
        super(stuff,ex);
    }
}
