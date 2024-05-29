package com.borlok.mvcSpine.security;

import com.borlok.mvcSpine.model.User;
import com.borlok.mvcSpine.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByPhoneNumber(username);
        if (user == null)
            throw new UsernameNotFoundException("User with defined username: " + username + " not found");
        return JwtUserFactory.create(user);
    }
}
