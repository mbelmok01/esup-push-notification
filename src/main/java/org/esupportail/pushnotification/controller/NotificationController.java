/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esupportail.pushnotification.controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import org.esupportail.pushnotification.form.NotificationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    @RenderMapping(params = { "action=notificationForm" })
    public String notificationForm(Model model, @RequestParam ( value = "submit", required = false) String isSubmited) {
        
        
        model.addAttribute("submit", isSubmited);
        model.addAttribute("command", new NotificationForm());
        
        return "notification";
    }
    
    @ActionMapping("notificationSubmit")
    public void onNotificationFormSubmit(ActionRequest req, ActionResponse res, @ModelAttribute("notificationForm") NotificationForm notif, BindingResult results) {

        logger.info("----------------------------------");
        
        logger.info(notif.getMessage());
        
        logger.info("----------------------------------");
        
        res.setRenderParameter("action", "notificationForm");
        res.setRenderParameter("submit", "isSubmited");
    }
}
