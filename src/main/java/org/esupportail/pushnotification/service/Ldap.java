/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package org.esupportail.pushnotification.service;

import java.util.ArrayList;
import java.util.List;
import org.esupportail.commons.exceptions.GroupNotFoundException;
import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.services.ldap.LdapException;
import org.esupportail.commons.services.ldap.LdapGroup;
import org.esupportail.commons.services.ldap.LdapGroupService;
import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserAndGroupService;
import org.esupportail.pushnotification.exceptions.LdapGroupNotFoundException;
import org.esupportail.pushnotification.exceptions.LdapUserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.springframework.ldap.support.LdapUtils;

/**
 *
 * @author mohamed
 */
public class Ldap {
    
    @Autowired
    private LdapUserAndGroupService ldapService;
    private LdapUtils ldapUtils;
    @Autowired
    private LdapGroupService ldapGroupService;
    @Autowired
    private LdapGroupService ldapGroupMembersService;
    private String searchAttribute;
    private String userPagerAttribute;
    
    
    private static final Logger logger = LoggerFactory.getLogger(Ldap.class);
    
    // return a List of LdapUser object from a list of string uid
    public ArrayList<LdapUser> getLdapUsersFromLogins(String recipients) throws LdapUserNotFoundException {
        
        // List of ldapUser object
        ArrayList<LdapUser> listOfLdapUser = new ArrayList<>();
        
        // list of user (string received by input)
        ArrayList<String> listOfStringUser = recipientToArrayList(recipients);
        
        for(String userUid : listOfStringUser) {
            try{
                
                LdapUser user = this.getLdapUserByUserUid(userUid);
                
                listOfLdapUser.add(user);
                
            } catch (LdapUserNotFoundException e) {
                
                logger.info("User  ["+ userUid + "] has not been found : ", e);
            }
        }
        return listOfLdapUser;
    }
    
    
    // return a LdapUser from a uid
    public LdapUser getLdapUserByUserUid(final String ldapUserUid) throws LdapUserNotFoundException, LdapException {
        
        LdapUser ldapUser = null;
        try {
            
            ldapUser = ldapService.getLdapUser(ldapUserUid);
            
        } catch (UserNotFoundException e) {
            
            throwLdapUserNotFoundException(e, ldapUserUid);
        }
        
        return ldapUser;
    }
    
    
    // return a LdapUser from a token. This function is not completed
    public List<LdapUser> getUserFromToken(String token) throws LdapUserNotFoundException{
        
        System.out.println("Le patern est : " + token);
        
        List<LdapUser> users = this.getLdapUsersFromToken(token);
        
        System.out.println("Utilisateurs trouves : ");
        
        return users;
    }
    
    public List<LdapUser> getLdapUsersByGroupId(final String groupId) throws LdapGroupNotFoundException, LdapUserNotFoundException {
        
        System.out.println("###############################################################");
        System.out.println("Entree dans getLdapUsersByGroupId");
        System.out.println("Le group a chercher est : " + groupId);
        
        
        List<LdapGroup> groups = null;
        
        try{
            
            groups = ldapGroupService.getLdapGroupsFromToken(groupId);
        
        } catch (GroupNotFoundException e){
            
            LdapGroupNotFoundException(e, groupId);
        }
        
        System.out.println("Nombre de groupes correspondants : " + groups.size());
        
        for(LdapGroup group : groups){
            
            System.out.println("Groupe correspondant => " + group.getId());
        }
        System.out.println("###############################################################");
        
           
        
        
        
        
        
        System.out.println("***************************************************************");
        
        LdapGroup ldapGroup = ldapGroupService.getLdapGroup(groups.get(0).getId());
        
        System.out.println("Group [ " + ldapGroup.getId() + " ] trouve. \n Recuperation des membres du groupe...");
        
        List<LdapUser> ldapUserList = ldapService.getMembers(ldapGroup);

        System.out.println(" Nombre de membres trouve : " + ldapUserList.size());

        
        System.out.println("Liste des membres du groupe : " + ldapUserList.toString());
        
        System.out.println("Listing des membres du groupe : ");
        
        for(LdapUser ldapUser : ldapUserList){
            System.out.println("login : " + ldapUser.getId());
        }
        
        System.out.println("***************************************************************");
        
        return ldapUserList;
    }
    
    public List<LdapGroup> searchGroupsByName(final String token) {
        System.out.println("Entree dans searchGroupsByName");
        
        List<LdapGroup> groups = ldapGroupService.getLdapGroupsFromToken(token);
        
        for (LdapGroup group : groups) {
            System.out.println("nom du groupe : " + group.getId());
            List<String> membres = this.getMemberIds(group.getId());
            System.out.println("Liste des membres du groupe :");
            for(String login : membres){
                System.out.println("login => " + login);
            }
        }
        
        return ldapGroupService.getLdapGroupsFromToken(token);
    }
    
    public List<String> getMemberIds(String groupId) {
        return ldapService.getMemberIds(ldapGroupMembersService.getLdapGroup(groupId));
    }
    
    public List<String> getLoginsFromLdapUsers(List<LdapUser> ldapUsers){
        
        List<String> logins = null;
        
        for(LdapUser ldapUser : ldapUsers) {
            logins.add(ldapUser.getId());
        }
        
        return logins;
    }
    
    private void throwLdapUserNotFoundException(UserNotFoundException e, final String ldapUserUid) throws LdapUserNotFoundException {
        
        final String messageStr = "Impossible de trouver l'utilisateur ayant pour login : [" + ldapUserUid + "]";
        logger.debug(messageStr, e);
        throw new LdapUserNotFoundException(messageStr, e);
    }
    
    private void LdapGroupNotFoundException(GroupNotFoundException e, String groupId) throws LdapGroupNotFoundException {
        
        final String messageStr = "Impossible de trouver le ayant pour id : [" + groupId + "]";
        logger.debug(messageStr, e);
        throw new LdapGroupNotFoundException(messageStr, e);
        
    }
    
    public List<LdapUser> getLdapUsersFromToken(final String token) {
        System.out.println("Entree dans getLdapUsersFromToken");
        System.out.println("Le toekn est : " + token);
        final AndFilter filter = new AndFilter();
        andTokenFilter(filter, token);
        return searchWithFilter(filter);
    }
    
    private void andTokenFilter(final AndFilter filter, final String token) {
        
        for (String tok : token.split("\\p{Blank}")) {
            if (tok.length() > 0)
                filter.and(new WhitespaceWildcardsFilter(searchAttribute, tok));
        }
    }
    
    public List<LdapUser> searchWithFilter(final Filter filter) {
        
        final String filterAsStr = filter.encode();
        if (logger.isDebugEnabled()) {
            logger.debug("LDAP filter applied : " + filterAsStr);
        }
        return ldapService.getLdapUsersFromFilter(filterAsStr);
    }
    
    public void setUserSearchAttribute(final String searchAttribute) {
        
        this.searchAttribute = searchAttribute;
    }
    
    public void setLdapService(final LdapUserAndGroupService ldapGroupService) {
        
        this.ldapService = ldapGroupService;
    }
    
    public ArrayList<String> recipientToArrayList(String recipients) {
        
        ArrayList<String> recipientsList = new ArrayList();
        String [] parts = recipients.split(",");
        for (int i = 0; i< parts.length; i++) {
            System.out.println("un recipient : " + parts[i]);
            recipientsList.add(parts[i]);
        }
        return recipientsList;
    }
    
    
}
