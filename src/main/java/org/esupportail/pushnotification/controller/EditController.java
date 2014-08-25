package org.esupportail.pushnotification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
