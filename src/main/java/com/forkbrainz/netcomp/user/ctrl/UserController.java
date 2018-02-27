/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forkbrainz.netcomp.user.ctrl;

import com.forkbrainz.netcomp.user.dto.UserRegistrationDto;
import com.forkbrainz.netcomp.user.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author mohyasir
 */
@Controller
public class UserController {
    
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
    
    @Autowired
    private UserService userService;
    
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
        System.out.println("User dto recieved: "+userDto);
        UserDetails existing = userService.loadUserByUsername(userDto.getEmail());
        if (existing != null){
            System.out.println("reject existing");
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            System.out.println("result has errors: ");
            return "registration";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }
}
