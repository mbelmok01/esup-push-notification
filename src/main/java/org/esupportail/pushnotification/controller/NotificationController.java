/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package org.esupportail.pushnotification.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserAndGroupService;
import org.esupportail.pushnotification.exceptions.LdapGroupNotFoundException;
import org.esupportail.pushnotification.exceptions.LdapUserNotFoundException;
import org.esupportail.pushnotification.form.NotificationForm;
import org.esupportail.pushnotification.service.Ldap;
import org.esupportail.pushnotification.service.Mail;
import org.jboss.aerogear.unifiedpush.JavaSender;
import org.jboss.aerogear.unifiedpush.SenderClient;
import org.jboss.aerogear.unifiedpush.message.MessageResponseCallback;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private Ldap ldap;
    @Autowired
    private Mail mail;
    @Autowired
    private LdapUserAndGroupService ldapService;
//    private LdapUtils ldapUtils;
    
    
    private String mailAttribute = "mail";
    
    @Value("${push.rootServerURL}") private String url;
    @Value("${push.applicationId}") private String applicationId;
    @Value("${push.masterSecret}") private String masterSecret;
    @Value("{smtp.user}") private String sender;
    
    @PostConstruct
    public void initConnection() throws MessagingException {
        
        this.defaultJavaSender = new SenderClient.Builder(this.url).build();
    }
    
    @RenderMapping(params = { "action=notificationForm" })
    public String notificationForm(RenderRequest req, Model model, @RequestParam ( value = "submit", required = false) String isSubmited) {
        
        model.addAttribute("submit", isSubmited);
        model.addAttribute("command", new NotificationForm());
        
        return "notification";
    }
    
    @ActionMapping("notificationSubmit")
    public void onNotificationFormSubmit(ActionRequest req, ActionResponse res, @ModelAttribute("notificationForm") NotificationForm notification, BindingResult results) throws LdapUserNotFoundException, LdapGroupNotFoundException, MessagingException {
        
        logger.info("The recipient type is : " + notification.getRecipientType());
        logger.info("The message : " + notification.getMessage());
        logger.info("The recipient : " + notification.getRecipient());
        logger.info("Mail : " + notification.getMail());
        
        
        
        switch(notification.getRecipientType()) {
            case "token" : 
                this.getUserByToken(notification);
            case "logins" :
                this.sendNotificationToLogins(notification);
                break;
            case "groups" :
                this.sendNotificationToGroups(notification);
                break;
            case "broadcast" :
                this.sendNotification(notification, null);
                break;
        }
        
        res.setRenderParameter("action", "notificationForm");
        res.setRenderParameter("submit", "isSubmited");
    }
    
    public void sendNotificationToLogins(NotificationForm notification) throws LdapUserNotFoundException {

        // this.getLdapUsersFromLogins(notification.getRecipient());
        this.sendNotification(notification, this.recipientToArrayList(notification.getRecipient()));
        
        this.sendMailQuestion(notification);
    }
    
    public void sendNotificationToGroups(NotificationForm notification) throws LdapGroupNotFoundException, LdapUserNotFoundException {
        
        List<LdapUser> ldapUsers = ldap.getLdapUsersByGroupId(notification.getRecipient());
//        ldap.searchGroupsByName(notification.getRecipient());
        
        // recuperer le login de chaque utilisateur
        List<String> logins = ldap.getLoginsFromLdapUsers(ldapUsers);
        
        for(String login : logins){
            System.out.print(login);
        }
        // appeler send notification to logins
        this.sendNotification(notification, logins);
        
    }
    
    
    
    public void sendNotification(NotificationForm notification, List<String> logins){
        
        this.defaultJavaSender = new SenderClient.Builder(this.url).build();
        
        UnifiedMessage unifiedMessage = new UnifiedMessage.Builder()
                .pushApplicationId(this.applicationId)
                .masterSecret(this.masterSecret)
                .alert("Hello from Java Sender API!")
                .build();
        
        defaultJavaSender.send(unifiedMessage, new MessageResponseCallback() {
            @Override
            public void onComplete(int statusCode) {
                
                //do cool stuff
                System.out.print("It's work fine !!! \n");
            }
            
            @Override
            public void onError(Throwable throwable) {
                //bring out the bad news
                System.out.print("It doesn't work !!! \n");
            }
        });
        
    }
    
    public void sendCourriel(NotificationForm notification) throws LdapUserNotFoundException{
        
        List<LdapUser> ldapUsers = ldap.getLdapUsersFromLogins(notification.getRecipient());
        
        System.out.print("Le groupe : " + notification.getRecipient() + " contient " + ldapUsers.size() + " membres \n");
        for(LdapUser ldapUser : ldapUsers){
            
            String msg = notification.getMessage();
            String object = notification.getObject();
            
            //mail.sendMail(this.sender, ldapUser.getAttribute(this.mailAttribute),object, msg);
        }
    }
    
    
    public ArrayList<String> recipientToArrayList(String recipients) {
        
        ArrayList<String> recipientsList = new ArrayList();
        
        String [] parts = recipients.split(",");
        
        for (int i = 0; i< parts.length; i++) {
        
            recipientsList.add(parts[i]);
        
        }
        
        return recipientsList;
    }
    
    private void getUserByToken(NotificationForm notification) {
        
        System.out.print("Etree dans getUserByToken \n");
        
        List<LdapUser> users = ldapService.getLdapUsersFromToken(notification.getRecipient());
        
        for(LdapUser user : users){
        
            System.out.print("le nom du user est : "+ user.getId() + "\n");
        
        }
    }
    
    
    
    public void sendMailQuestion(NotificationForm notification ) throws LdapUserNotFoundException {
        if(notification.getMail().equalsIgnoreCase("true")) {
            System.out.print("Joindre la notification d'un courriel \n");
            this.sendCourriel(notification);
        }
    }
}
