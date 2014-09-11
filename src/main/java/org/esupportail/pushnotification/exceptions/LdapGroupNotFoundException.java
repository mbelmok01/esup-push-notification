package org.esupportail.pushnotification.exceptions;

/**
 * 
 * @author Mohamed
 *
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