/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forkbrainz.netcomp.user.service;

import com.forkbrainz.netcomp.user.User;
import com.forkbrainz.netcomp.user.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Mohammad yasir
 */
public interface UserService extends UserDetailsService {
    public User findByEmail(String email);
    
    public User save(UserRegistrationDto dto);
}
