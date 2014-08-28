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
    
//    @ModelAttribute
//    public void getPreferences(RenderRequest request, Model model) {
//    	
//        PortletPreferences prefs = request.getPreferences();
//        try {
//            model.addAttribute("serverURL", prefs.getValue("serverURL", null));
//            model.addAttribute("applicationID", prefs.getValue("applicationID", null));
//            model.addAttribute("masterSecret", Boolean.valueOf(prefs.getValue("masterSecret", null)));
//        } catch(Exception e) {
//            // If we can not fetch preferences
//            logger.error("An error has been thrown when fetching preferences.");
//        }
//    }
    
//    @ActionMapping("deletePreferences)"
//    public void deletePreferences(ActionRequest request, ActionResponse response) {
//
//        PortletPreferences prefs = request.getPreferences();
//        try {
//            prefs.setValue("", null);
//            prefs.setValue("", null);
//            prefs.store();
//        } catch (Exception e) {}
//
//        response.setRenderParameter("isDeleted", "true");
//    }
        
    
//    @RenderMapping
//    public ModelAndView showPreferencesView(RenderRequest request,
//            RenderResponse response) throws Exception {
//
//        Map<String, Object> model = new HashMap<String, Object>();
//
//        PortletSession session = request.getPortletSession();
//        String setName = request.getPreferences().getValue("newsSetName", "default");
//        
//        
//        return new ModelAndView(viewName, "model", model);
//    }
}



