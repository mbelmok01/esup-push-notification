/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esupportail.pushnotification.form;

import java.util.ArrayList;

/**
 *
 * @author mohamed
 */
public class NotificationForm {
    private String recipient;
    private String message;
    private String recipientType;

    
//    private ArrayList<String> recipients = new ArrayList<String>();
    
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
    
    
//    public ArrayList<String> getRecipients() {
//        return this.recipients;
//    }
//    
//    public void setRecipients(ArrayList<String> recipients) {
//        this.recipients = recipients;
//    }
}
