/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author woodi
 */
public class NonExistentFieldException extends Exception{

    public NonExistentFieldException(String stuff, java.lang.Exception ex) {
        super(stuff,ex);
    }
    
    
}
