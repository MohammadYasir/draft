package com.forkbrainz.netcomp.security.service;

import com.forkbrainz.netcomp.security.model.User;
import com.forkbrainz.netcomp.security.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
