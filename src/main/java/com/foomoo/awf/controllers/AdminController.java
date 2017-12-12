package com.foomoo.awf.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Map;

/**
 * Controller to configure access to OneDrive.
 * Access to this controller should be authenticated using Microsoft AD over OAuth2.
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping
    public String getAdmin(final Authentication authentication, final Model model) {

        final Map<String, Object> details = (Map<String, Object>) ((OAuth2Authentication) authentication).getUserAuthentication()
                                                                                                         .getDetails();
        model.addAttribute("authDetails", details);

        return "admin";
    }
}
