package com.forkbrainz.netcomp.security.web;

import com.forkbrainz.netcomp.user.NetCompUser;
import java.security.Principal;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    @Qualifier("NetCompUserRepo")
    ObjectRepository<NetCompUser> userRepo;
    
    @GetMapping("/")
    public String root() {
        return "index";
    }
    
    @GetMapping("/me")
    public @ResponseBody NetCompUser currentUser(Principal principal){
        String uname = principal.getName();
        
        return new NetCompUser();
    }
    
    @PutMapping("/me/{id}")
    public @ResponseBody NetCompUser updateUser(@RequestBody NetCompUser user){
        return new NetCompUser();
    }
}
