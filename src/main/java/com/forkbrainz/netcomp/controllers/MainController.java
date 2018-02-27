/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forkbrainz.netcomp.controllers;

import com.forkbrainz.netcomp.user.NetCompUserDetailsService;
import com.forkbrainz.netcomp.user.User;
import com.forkbrainz.netcomp.user.dto.UserRegistrationDto;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author mohyasir
 */
@Controller
public class MainController {
    
    @Autowired
    private NetCompUserDetailsService userService;
    
    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    
    @PostMapping("/signup")
    public String register(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result) {
        UserDetails existing = userService.loadUserByUsername(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "registration";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }
    
    @GetMapping("/login-error.html")
    public String loginErr(Model model) {
        model.addAttribute("loginError", true);
        System.out.println("Login error");
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }
}
