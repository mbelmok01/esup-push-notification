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

public class LdapUserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4244684563927159925L;

	/**
	 * Default constructor.
	 */
	public LdapUserNotFoundException() {
		super();
	}
	
	/**
	 * Constructor with message.
	 * @param message
	 */
	public LdapUserNotFoundException(final String message) {
		super(message);
	}
	
	/**
	 * Constructor with message and cause.
	 * @param message
	 * @param t
	 */
	public LdapUserNotFoundException(final String message, final Throwable t) {
		super(message, t);
	}
		
	
}
