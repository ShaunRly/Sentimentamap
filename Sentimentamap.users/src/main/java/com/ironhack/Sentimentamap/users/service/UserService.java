package com.ironhack.Sentimentamap.users.service;

import com.ironhack.Sentimentamap.users.dao.SiteUser;
import com.ironhack.Sentimentamap.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public SiteUser getUserDetailsByLogin(String username) {
        Optional<SiteUser> userDetails = userRepository.findByUsername(username);
        return userDetails.orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> userDetails = userRepository.findByUsername(username);
        return new User(userDetails.get().getUsername(),userDetails.get().getEncodedPassword(), true, true, true, true, new ArrayList<>());

    }
}
