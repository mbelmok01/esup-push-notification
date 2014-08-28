/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.esupportail.pushnotification.controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import org.esupportail.pushnotification.form.EditForm;
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
@RequestMapping("EDIT")
public class EditController {
    
    private static final Logger logger = LoggerFactory.getLogger(EditController.class);
    
    @RenderMapping
    public String editForm(RenderRequest req, Model model, @RequestParam ( value = "submit", required = false) String isSubmited) {
        
        model.addAttribute("command", new EditForm());
        
        return "edit";
    }
    
    @ActionMapping("editSubmit")
    public void onEditFormSubmit(ActionRequest req, ActionResponse res, @ModelAttribute("editForm") EditForm parameters, BindingResult results) {
        
        logger.info("The sereur URL : " + parameters.getServerURL());
        logger.info("The application ID : " + parameters.getApplicationID());
        logger.info("The master secret : " + parameters.getMasterSecret());
        
        res.setRenderParameter("action", "editForm");
    }
}



