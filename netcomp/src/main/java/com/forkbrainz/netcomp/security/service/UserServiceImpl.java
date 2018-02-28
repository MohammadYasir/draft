package com.forkbrainz.netcomp.security.service;

import com.forkbrainz.netcomp.security.model.User;
import com.forkbrainz.netcomp.security.repository.UserRepository;
import com.forkbrainz.netcomp.security.web.dto.UserRegistrationDto;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import org.springframework.security.authentication.BadCredentialsException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(UserRegistrationDto registration, String confirmToken){
        User user = new User();
        user.setName(registration.getName());
        user.setEmail(registration.getEmail());
        user.setPhone(registration.getPhone());
        user.setConfirmationToken(confirmToken);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        System.out.println("login with: "+user);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        if(user.getPassword() == null){
            throw new BadCredentialsException("You need to set your password first using the activation link sent to your email ID.");
        }
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), roles);
    }

    @Override
    public User findByConfirmationToken(String token) {
        return userRepository.findByConfirmationToken(token);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
