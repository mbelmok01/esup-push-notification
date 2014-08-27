package org.esupportail.pushnotification.controller;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.util.HashMap;
import java.util.Map;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.esupportail.pushnotification.form.EditForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohamed
 */
@Controller
@RequestMapping("EDIT")
public class EditController {

    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);
    
    @RenderMapping(params = { "action=editForm" })
    public String edit(RenderRequest req, Model model) {
        
        model.addAttribute("command", new EditForm());
        
        return "edit";
    }
    
    
    @ActionMapping("editSubmit")
    public void onEditFormSubmit(ActionRequest req, ActionResponse res, @ModelAttribute("parameterForm") EditForm paramters, BindingResult results) {
        
        logger.info("The sereur URL : " + paramters.getServerURL());
        logger.info("The application ID : " + paramters.getApplicationID());
        logger.info("The master secret : " + paramters.getMasterSecret());
        
        res.setRenderParameter("action", "editForm");
    }
    
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



