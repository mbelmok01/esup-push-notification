package org.esupportail.pushnotification.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * Handles requests for the portlet view mode.
 */
@Controller
@RequestMapping("VIEW")
public class ViewController {
    
    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);
    
    /**
     * Simply selects the home view to render by returning its name.
     * @param locale
     * @param model
     * @return 
     * @throws java.io.IOException
     */
    
    @RenderMapping
    public String home(Locale locale, Model model) {
        System.out.println("HEllo World");
        logger.info("Welcome home! the client locale is "+ locale.toString());

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "home";
    }
}