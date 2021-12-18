package com.ironhack.Sentimentamap.users.service;

import com.ironhack.Sentimentamap.users.dao.SiteUser;
import com.ironhack.Sentimentamap.users.dto.UpdateUserDTO;
import com.ironhack.Sentimentamap.users.dto.UserCreationDTO;
import com.ironhack.Sentimentamap.users.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private BCryptPasswordEncoder encoder;
    private Environment environment;

    public UserService(BCryptPasswordEncoder encoder){
        this.encoder = encoder;
    }

    public SiteUser getUserDetailsByLogin(String username) {
        Optional<SiteUser> userDetails = userRepository.findByUsername(username);
        return userDetails.orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> userDetails = userRepository.findByUsername(username);
        return new User(userDetails.get().getUsername(),userDetails.get().getEncodedPassword(), true, true, true, true, new ArrayList<>());

    }

    public SiteUser updateUser(String token, UpdateUserDTO updateUserDTO) {
        token = token.replace("Bearer","");
        Long tokenSubject = Long.parseLong(Jwts.parser()
                .setSigningKey(environment.getProperty("token.secret"))
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
        Optional<SiteUser> user = userRepository.findById(tokenSubject);
        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }else{
            user.get().setName(updateUserDTO.getName());
            user.get().setProfileURL(updateUserDTO.getImageUrl());
            user.get().setEmail(updateUserDTO.getEmail());

            SiteUser updatedUser = userRepository.save(user.get());
            return updatedUser;
        }
    }

    public SiteUser createUser(UserCreationDTO userDTO) {
        Optional<SiteUser> existingUser = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User already exists!");
        }
        SiteUser userEntity = new SiteUser(userDTO.getUsername(),encoder.encode(userDTO.getPassword()), userDTO.getEmail());
        SiteUser savedUserEntity = userRepository.save(userEntity);
        return savedUserEntity;
    }

    public boolean getUserDetails(String token, String username, String password) {
        Optional<SiteUser> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()){
            return encoder.matches(password, existingUser.get().getEncodedPassword());
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User with that Username");
        }
    }


}
