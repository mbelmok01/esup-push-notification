/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esupportail.pushnotification.form;

/**
 *
 * @author mohamed
 */
public class NotificationForm {
    private String recipient;
    private String message;
    private String recipientType;
    private String mail;
    private String object;

    public String getRecipient() {
        return this.recipient;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }
    
    public String getMail() {
        return mail;
    }

    public void setCourriel(String courriel) {
        this.mail = courriel;
    }
    
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
