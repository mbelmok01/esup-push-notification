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
import org.esupportail.commons.exceptions.GroupNotFoundException;
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
    @Autowired private Ldap ldap;
    @Autowired private Mail mail;
    @Autowired private LdapUserAndGroupService ldapService;
    @Value("${push.rootServerURL}") private String url;
    @Value("${push.applicationId}") private String applicationId;
    @Value("${push.masterSecret}") private String masterSecret;
    @Value("{smtp.user}") private String sender;
    private JavaSender defaultJavaSender;
    private String mailAttribute = "mail";
    
    
    
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
        
        switch(notification.getRecipientType()) {
            case "token" :
                ldap.getUsersByToken(notification);
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
        
        ArrayList<String> logins = this.recipientToArrayList(notification.getRecipient());
        
        List<LdapUser> ldapUsers = ldap.getLdapUsersFromUids(logins);
        
        this.sendNotification(notification, logins);
        
        this.addMail(notification, ldapUsers);
    }
    
    public void sendNotificationToGroups(NotificationForm notification) throws LdapGroupNotFoundException, LdapUserNotFoundException {
        
        String groupId = notification.getRecipient();
        
        List<LdapUser> ldapUsers = null;
        
        try{
            
            ldapUsers = ldap.getLdapUsersByGroupId(groupId);
        
        } catch (GroupNotFoundException e){
            
            LdapGroupNotFoundException(e, groupId);
        
        }     
//        List<String> logins = null;
//        
//        for(LdapUser user : ldapUsers){
//            
//            logger.info("user id : " + user.getId() + " user email : " + user.getAttribute("email"));
//            logins.add(user.getId());
//            
//        }
        
//        this.sendNotification(notification, logins);
        
        this.addMail(notification, ldapUsers);
        
    }
    
    public void sendNotification(NotificationForm notification, List<String> logins){
        
        this.defaultJavaSender = new SenderClient.Builder(this.url).build();
        
        UnifiedMessage unifiedMessage = new UnifiedMessage.Builder()
                .pushApplicationId(this.applicationId)
                .masterSecret(this.masterSecret)
                .alert(notification.getMessage())
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
    
    public void addMail(NotificationForm notification, List<LdapUser> users ) throws LdapUserNotFoundException {
        
        if(notification.getMail().equalsIgnoreCase("true")) {
            
            System.out.print("Joindre la notification d'un courriel \n");
            this.sendCourriel(notification, users);
            
        }
    }
    
    public void sendCourriel(NotificationForm notification, List<LdapUser> users) throws LdapUserNotFoundException{
       
        for(LdapUser ldapUser : users){
            
            String msg = notification.getMessage();
            String object = notification.getObject();
            
            //mail.sendMail(this.sender, ldapUser.getAttribute(this.mailAttribute), object, msg);
        }
    }
    
    public ArrayList<String> recipientToArrayList(String recipients) {
        
        ArrayList<String> recipientsList = new ArrayList<String>();
        
        String [] parts = recipients.split(",");
        
        for(String recipient : parts){
            recipientsList.add(recipient);
        }
        
        return recipientsList;
    }   

    private void LdapGroupNotFoundException(GroupNotFoundException e, String groupId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}