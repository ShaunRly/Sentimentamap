package com.ironhack.Sentimentamap.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationDTO {

    private String username;
    private String password;
    private String email;
}
