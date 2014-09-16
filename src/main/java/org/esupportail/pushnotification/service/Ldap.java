/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package org.esupportail.pushnotification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import org.esupportail.commons.exceptions.GroupNotFoundException;
import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.services.ldap.LdapException;
import org.esupportail.commons.services.ldap.LdapGroup;
import org.esupportail.commons.services.ldap.LdapGroupService;
import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserAndGroupService;
import org.esupportail.pushnotification.exceptions.LdapGroupNotFoundException;
import org.esupportail.pushnotification.exceptions.LdapUserNotFoundException;
import org.esupportail.pushnotification.form.NotificationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import static org.springframework.ldap.query.LdapQueryBuilder.query;
import org.springframework.ldap.support.LdapUtils;

/**
 *
 * @author mohamed
 */
public class Ldap {
    
    @Autowired private LdapUserAndGroupService ldapService;
//    @Autowired private LdapGroupService ldapGroupService;
    @Autowired private LdapGroupService ldapGroupMembersService;
    @Autowired private LdapTemplate ldapTemplate;
    @Value("${ldap.group.groupMemberAttr}")
    private String groupMemberAttribute;
    private LdapUtils ldapUtils;
    private String searchAttribute;
    private String userPagerAttribute;
    private static final Logger logger = LoggerFactory.getLogger(Ldap.class);
    
    // ok
    public LdapUser getLdapUserByUserUid(final String ldapUserUid) throws LdapUserNotFoundException, LdapException {
        logger.info("entree dans getLdapUserByUserUid");
        LdapUser ldapUser = null;
        try {
            
            ldapUser = ldapService.getLdapUser(ldapUserUid);
            logger.info(ldapUser.toString());
            
        } catch (UserNotFoundException e) {
            
            throwLdapUserNotFoundException(e, ldapUserUid);
        }
        
        return ldapUser;
    }

    // ok
    public ArrayList<LdapUser> getLdapUsersFromUids(ArrayList<String> logins) throws LdapUserNotFoundException {
        
        // List of ldapUser object
        ArrayList<LdapUser> listOfLdapUser = new ArrayList<LdapUser>();
        
        for(String userUid : logins) {
            try{
                
                logger.info("Login du user a chercher : " + userUid);
                
                LdapUser user = this.getLdapUserByUserUid(userUid);
                
                logger.info("User trouve : " + user.toString());
                
                listOfLdapUser.add(user);
                
            } catch (LdapUserNotFoundException e) {
                
                logger.info("Le user ["+ userUid + "] n\'a pas ete trouve : ", e);
            }
        }
        return listOfLdapUser;
    }
    
    // not ok
    public void getUsersByToken(NotificationForm notification) {
        
        logger.info("Etree dans getUserByToken");
        
        List<LdapUser> users = ldapService.getLdapUsersFromToken(notification.getRecipient());
        
        for(LdapUser user : users){
            
            logger.info("User correspondant au token : "+ user.getId());
            
        }
    }
    
//    public void getLdapUserByGrouptoken(String groupToken) throws LdapGroupNotFoundException {
//
//        try{
//            logger.info("###############################################################");
//
//            List<LdapGroup> groups = ldapGroupMembersService.getLdapGroupsFromToken(groupToken);
//
//            logger.info("Nombre de correspondances : " + groups.size());
//
//            for(LdapGroup group : groups){
//
//                logger.info("Groupe correspondant => " + group.getId());
//            }
//
//            logger.info("###############################################################");
//
//        } catch (GroupNotFoundException e){
//
//            LdapGroupNotFoundException(e, groupToken);
//        }
//
//    }
    
    public List<LdapUser> getLdapUsersByGroupId(final String groupId) throws LdapGroupNotFoundException, LdapUserNotFoundException, NullPointerException {
        
        logger.info("Entree dans getLdapUsersByGroupId");
        logger.info("Le group a chercher est : " + groupId);
        
        List<LdapUser> ldapUserList = null;
        
        try{
            logger.info("Entree dans try");
            
            LdapGroup ldapGroup = ldapGroupMembersService.getLdapGroup(groupId);
            
            logger.info("ldapGroup => " + ldapGroup.toString());
            
            List<LdapUser> users = this.getMembers(ldapGroup);
            
            logger.info(users.toString());
            
//            logger.info("Groupe trouve : " + ldapGroup.toString());
//
//            List<String> membres = this.getMemberIds(ldapGroup.getId());
//
//            logger.info("Liste des membres du groupe :");
//            for(String login : membres){
//                logger.info("Login : " + login);
//            }
//
//            ldapUserList = this.getMembers(ldapGroup);
//
//            logger.info("Nombre de membres trouve : " + ldapUserList.size());
//
//            logger.info("Listing des membres du groupe : ");
//
//            for(LdapUser ldapUser : ldapUserList){
//
//                logger.info("Login : " + ldapUser.getId() + " \n mail : " + ldapUser.getAttribute("mail"));
//            }
//
////            for (String user : membres){
////                logger.info("User : " + user);
////            }
//
            
        } catch (GroupNotFoundException e){
            
            LdapGroupNotFoundException(e, groupId);
        }
        
        return ldapUserList;
    }
    
    // not ok
//    public List<LdapGroup> searchGroupsByName(final String token) {
//        logger.info("Entree dans searchGroupsByName");
//
//        List<LdapGroup> groups = ldapGroupMembersService.getLdapGroupsFromToken(token);
//
//        for (LdapGroup group : groups) {
//            logger.info("Nom du groupe : " + group.getId());
//            List<String> membres = this.getMemberIds(group.getId());
//            logger.info("Liste des membres du groupe :");
//            for(String login : membres){
//                logger.info("Login : " + login);
//            }
//        }
//
//        return ldapGroupMembersService.getLdapGroupsFromToken(token);
//    }
    
    // ok
//    public List<String> getMembersId(LdapGroup groupId) {
//        return ldapService.getMemberIds(ldapGroupMembersService.getLdapGroup(groupId));
//    }
    
    
    
    
    // ok
    public List<LdapUser> getMembers(LdapGroup group) throws LdapUserNotFoundException {
        
        logger.info("Entree dans getMembers");
        
        List<LdapUser> users = new ArrayList<LdapUser>();
        
        List<String> usersId = group.getAttributes(groupMemberAttribute);
        
        logger.info("Liste des usersId :");
        ArrayList<String> uids = new ArrayList<String>();
        
        for(String userId : usersId) {
            
            logger.info("Un user : " + userId);
            
            Pattern p = Pattern.compile("uid=(.+?),");
            Matcher m = p.matcher(userId);
            
            while(m.find()) {
                
                String uid = m.group(1);
                uids.add(uid);
                
//                try {
//                    logger.info("Entree dans le try : ");
//                    logger.info("uid a chercher est : " + uid);
//                    LdapUser u = this.getLdapUserByUserUid(uid);
//                    logger.info("Recuperation du ldapUser : " + u.toString());
//                    users.add(u);
//                
//                } catch (Exception e) {}
            }
        }
        users = this.getLdapUsersFromUids(uids);
        logger.info(users.toString());
        return users;
    }
    
    // ok
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }
    
    // ok
    public List<String> getLoginsFromLdapUsers(List<LdapUser> ldapUsers){
        
        List<String> logins = null;
        
        for(LdapUser ldapUser : ldapUsers) {
            logins.add(ldapUser.getId());
        }
        
        return logins;
    }
    
    // ok
    public void setUserSearchAttribute(final String searchAttribute) {
        
        this.searchAttribute = searchAttribute;
    }
    
    // ok
    public void setLdapService(final LdapUserAndGroupService ldapGroupService) {
        
        this.ldapService = ldapGroupService;
    }
    
    // ok
    public ArrayList<String> recipientToArrayList(String recipients) {
        
        ArrayList<String> recipientsList = new ArrayList<String>();
        
        String [] parts = recipients.split(",");
        
        for(String recipient : parts){
            recipientsList.add(recipient);
        }
        
        return recipientsList;
    }
    
    // ok
    private void LdapGroupNotFoundException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // ok
    private void throwLdapUserNotFoundException(UserNotFoundException e, final String ldapUserUid) throws LdapUserNotFoundException {
        
        final String messageStr = "Impossible de trouver l'utilisateur ayant pour login : [" + ldapUserUid + "]";
        logger.debug(messageStr, e);
        throw new LdapUserNotFoundException(messageStr, e);
    }
    
    // ok
    private void LdapGroupNotFoundException(GroupNotFoundException e, String groupId) throws LdapGroupNotFoundException {
        
        final String messageStr = "Impossible de trouver le ayant pour id : [" + groupId + "]";
        logger.debug(messageStr, e);
        throw new LdapGroupNotFoundException(messageStr, e);
        
    }
}
