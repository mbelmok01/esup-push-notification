/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package org.esupportail.pushnotification.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;


/**
 *
 * @author mohamed
 */
public class Mail implements MailSender {
    
    private MailSender mailSender;
    private SimpleMailMessage templateMessage;
    
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public void sendMail(String from, String to, String subject, String msg) {
        //creating message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        //sending message
        mailSender.send(message);
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void send(SimpleMailMessage[] simpleMessages) throws MailException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}