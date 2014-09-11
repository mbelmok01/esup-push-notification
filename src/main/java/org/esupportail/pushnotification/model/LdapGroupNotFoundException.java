/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package org.esupportail.pushnotification.model;

/**
 *
 * @author mohamed
 */
public class LdapGroupNotFoundException extends Exception {
    
    /**
     * Default constructor.
     */
    public LdapGroupNotFoundException() {
        super();
    }
    
    /**
     * Constructor with message.
     * @param message
     */
    public LdapGroupNotFoundException(final String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause.
     * @param message
     * @param t
     */
    public LdapGroupNotFoundException(final String message, final Throwable t) {
        super(message, t);
    }
}