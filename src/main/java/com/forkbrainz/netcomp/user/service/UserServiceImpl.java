/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forkbrainz.netcomp.user.service;

import com.forkbrainz.netcomp.user.User;
import com.forkbrainz.netcomp.user.dto.UserRegistrationDto;
import com.forkbrainz.netcomp.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Mohammad yasir
 */
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public User findByEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User save(UserRegistrationDto dto) {
        User user = new User();
        user.setUsername(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
