package org.esupportail.pushnotification.exceptions;

/**
 * 
 * @author Mohamed
 *
 */
public class LdapUserNotFoundException extends Exception {

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
