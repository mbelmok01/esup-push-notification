/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esupportail.pushnotification.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import org.esupportail.pushnotification.form.NotificationForm;
import org.jboss.aerogear.unifiedpush.JavaSender;
import org.jboss.aerogear.unifiedpush.SenderClient;
import org.jboss.aerogear.unifiedpush.message.MessageResponseCallback;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 *
 * @author mohamed
 */
@Controller    
@RequestMapping("VIEW")
public class NotificationController {
    
    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);
    
    private JavaSender defaultJavaSender;
    
    @Value("${ups.url}") private String url;
    @Value("${ups.applicationId}") private String applicationId;
    @Value("${ups.masterSecret}") private String masterSecret;
    
    @PostConstruct
    public void setVariables() {
        this.defaultJavaSender = new SenderClient.Builder(this.url).build();
    }
    
    @RenderMapping(params = { "action=notificationForm" })
    public String notificationForm(RenderRequest req, Model model, @RequestParam ( value = "submit", required = false) String isSubmited) {
        
        
        model.addAttribute("submit", isSubmited);
        model.addAttribute("command", new NotificationForm());
        
        return "notification";
    }
    
    @ActionMapping("notificationSubmit")
    public void onNotificationFormSubmit(ActionRequest req, ActionResponse res, @ModelAttribute("notificationForm") NotificationForm notification, BindingResult results) {
        
        logger.info("The recipient type is : " + notification.getRecipientType());
        logger.info("The message : " + notification.getMessage());
        logger.info("The recipient : " + notification.getRecipient());
        
        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add(notification.getRecipient());
        
        this.sendNotification(notification.getMessage(), recipients);
        
        res.setRenderParameter("action", "notificationForm");
        res.setRenderParameter("submit", "isSubmited");
        
        
    }
    
    public void sendNotification(String message, List<String> recipients) {
            
        UnifiedMessage unifiedMessage = new UnifiedMessage.Builder()
                    .pushApplicationId(this.applicationId)
                    .masterSecret(this.masterSecret)
                    .alert(message)
                    .aliases(recipients)
                    .build();
            
            MessageResponseCallback callback = new MessageResponseCallback() {
                
                public void onComplete(int statusCode) {
                    //do good stuff
                    logger.info("Le code status : " + statusCode);
                }
                
                @Override
                public void onError(Throwable throwable) {
                    //bring out the bad news
                    logger.info("Il y a eu une erreur : "+ throwable);
                }
            };
            
            defaultJavaSender.send(unifiedMessage, callback);
            
        }
}
