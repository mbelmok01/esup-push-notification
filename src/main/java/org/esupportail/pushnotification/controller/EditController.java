package org.esupportail.pushnotification.controller;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.util.HashMap;
import java.util.Map;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
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
    
    @RenderMapping
    public String edit(Model model) {
        model.addAttribute("foo", "bar");
        
        return "edit";
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
