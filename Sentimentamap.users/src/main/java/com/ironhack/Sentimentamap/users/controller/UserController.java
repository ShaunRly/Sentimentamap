package com.ironhack.Sentimentamap.users.controller;

import com.ironhack.Sentimentamap.users.dao.SiteUser;
import com.ironhack.Sentimentamap.users.dto.UpdateUserDTO;
import com.ironhack.Sentimentamap.users.dto.UserCreationDTO;
import com.ironhack.Sentimentamap.users.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userDB")
public class UserController {

    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new_user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SiteUser createUser(@RequestBody UserCreationDTO userDTO) {
        System.out.println(userDTO.getUsername());
        System.out.println(userDTO.getUsername());
        return userService.createUser(userDTO);
    }

    @GetMapping("/user_details")
    @ResponseStatus(HttpStatus.OK)
    public boolean getUserDetails(@RequestHeader (name="Authorization") String token,@RequestParam String username, @RequestParam String password){
        return userService.getUserDetails(token, username, password);
    }


    @PutMapping("/user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SiteUser updateUser(@RequestHeader (name="Authorization") String token,@RequestBody UpdateUserDTO updateUserDTO){
        return userService.updateUser(token,updateUserDTO);
    }
}