package com.ironhack.Sentimentamap.users.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String username;
    private String encodedPassword;
    private String name;
    private String email;
    private String profileURL;


    @Override
    public String toString() {
        return "SiteUser{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", encodedPassword='" + encodedPassword + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profileURL='" + profileURL + '\'' +
                '}';
    }
}
