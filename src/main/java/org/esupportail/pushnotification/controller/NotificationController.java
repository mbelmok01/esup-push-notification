/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package org.esupportail.pushnotification.controller;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.services.ldap.LdapException;
import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserAndGroupService;
import org.esupportail.pushnotification.form.NotificationForm;
import org.esupportail.pushnotification.model.LdapUserNotFoundException;
import org.jboss.aerogear.unifiedpush.JavaSender;
import org.jboss.aerogear.unifiedpush.SenderClient;
import org.jboss.aerogear.unifiedpush.message.MessageResponseCallback;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.support.LdapUtils;
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
    @Autowired
    private LdapUserAndGroupService ldapService;
    private LdapUtils ldapUtils;
    private boolean succes;
    
    @Value("${push.rootServerURL}") private String url;
    @Value("${push.applicationId}") private String applicationId;
    @Value("${push.masterSecret}") private String masterSecret;
    
    @RenderMapping(params = { "action=notificationForm" })
    public String notificationForm(RenderRequest req, Model model, @RequestParam ( value = "submit", required = false) String isSubmited) {
        
        model.addAttribute("submit", isSubmited);
        model.addAttribute("command", new NotificationForm());
        
        return "notification";
    }
    
    @ActionMapping("notificationSubmit")
    public void onNotificationFormSubmit(ActionRequest req, ActionResponse res, @ModelAttribute("notificationForm") NotificationForm notification, BindingResult results) throws LdapUserNotFoundException {
        
        logger.info("The recipient type is : " + notification.getRecipientType());
        logger.info("The message : " + notification.getMessage());
        logger.info("The recipient : " + notification.getRecipient());
        
        if(notification.getRecipientType().equalsIgnoreCase("broadcast")){
            this.broadcastNotification(notification);
        }
        
        //this.sendNotification(notification);
        //this.getLdapUserByUserUid(notification.getRecipient());
        
        this.testLdapConnection(notification.getRecipient());
        
        res.setRenderParameter("action", "notificationForm");
        res.setRenderParameter("submit", "isSubmited");
    }
    
    
    @PostConstruct
    public void initConnection() {
        
        this.defaultJavaSender = new SenderClient.Builder(this.url).build();
    }
    
    
    public void setLdapService(
            final LdapUserAndGroupService ldapGroupService) {
        this.ldapService = ldapGroupService;
    }
    
    public void sendNotification(NotificationForm notification) {
        
        UnifiedMessage unifiedMessage = new UnifiedMessage.Builder()
                .pushApplicationId(this.applicationId)
                .masterSecret(this.masterSecret)
                .alert(notification.getMessage())
                .aliases(this.recipientToArrayList(notification.getRecipient()))
                .build();
        
        MessageResponseCallback callback = new MessageResponseCallback() {
            
            public void onComplete(int statusCode) {
                //do good stuff
                logger.info("Le code status : " + statusCode);
                
            }
            
            @Override
            public void onError(Throwable throwable) {
                //bring out the bad news
                logger.info("Il y a eu une erreur dans login: "+ throwable);
            }
        };
        
        defaultJavaSender.send(unifiedMessage, callback);
        
    }
    
    public void broadcastNotification(NotificationForm notification) {
        
        UnifiedMessage unifiedMessage = new UnifiedMessage.Builder()
                .pushApplicationId(this.applicationId)
                .masterSecret(this.masterSecret)
                .alert(notification.getMessage())
                .build();
        
        MessageResponseCallback callback = new MessageResponseCallback() {
            
            public void onComplete(int statusCode) {
                //do good stuff
                logger.info("Le code status : " + statusCode);
                
            }
            
            @Override
            public void onError(Throwable throwable) {
                //bring out the bad news
                logger.info("Il y a eu une erreur dans broadcast : "+ throwable);
            }
        };
        
        defaultJavaSender.send(unifiedMessage, callback);
        
    }
    
    
    // from String to ArrayList
    public ArrayList<String> recipientToArrayList(String recipients) {
        
        ArrayList<String> recipientsList = new ArrayList();
        String [] parts = recipients.split(",");
        for (int i = 0; i< parts.length; i++) {
            System.out.println("un recipient : " + parts[i]);
            recipientsList.add(parts[i]);
        }
        return recipientsList;
    }
    
    // Display user's attributes
    public void displayUser(LdapUser user){
        System.out.print("user id : " + user.getId());
        System.out.print("user attributes : " + user.getAttributeNames().toString());
    }
    
    // test connection to ldap and try to make request
    public void testLdapConnection(String recipients) throws LdapUserNotFoundException{
                
        // List of ldapUser object
        ArrayList<LdapUser> listOfLdapUser = new ArrayList<LdapUser>();
        
        // list of user (string received by input)
        ArrayList<String> listOfStringUser = recipientToArrayList(recipients);
        
        for(int i=0; i<listOfStringUser.size(); i++){
            
            System.out.print("Le login du user est : " + listOfStringUser.get(i) + "\n");
            
            LdapUser user = this.getLdapUserByUserUid(listOfStringUser.get(i));
            
            System.out.print("Utilisateur trouve : \n" );
            System.out.print(user.toString() + "\n");
            
            listOfLdapUser.add(user);
        }
    }
    
    public LdapUser getLdapUserByUserUid(final String ldapUserUid) throws LdapUserNotFoundException, LdapException {
        
        System.out.print("Entree dans getLdapUserByUserUid \n ");
        
        System.out.print("Le login du user a chercher est : " + ldapUserUid + "\n ");
        
        LdapUser ldapUser = null;
        try {
            System.out.print("Entree dans le try \n ");
            ldapUser = ldapService.getLdapUser(ldapUserUid);
        } catch (UserNotFoundException e) {
            System.out.print("Entree dans le catch \n ");
            throwLdapUserNotFoundException(e, ldapUserUid);
        }
        
        return ldapUser;
    }
    
    private void throwLdapUserNotFoundException(UserNotFoundException e, final String ldapUserUid) throws LdapUserNotFoundException {
		final String messageStr = "Impossible de trouver l'utilisateur ayant pour login : [" + ldapUserUid + "]";
		logger.debug(messageStr, e);
		throw new LdapUserNotFoundException(messageStr, e);
	}
}
