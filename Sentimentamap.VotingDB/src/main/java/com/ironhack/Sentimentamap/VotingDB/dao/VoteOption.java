package com.ironhack.Sentimentamap.VotingDB.dao;

import com.ironhack.Sentimentamap.VotingDB.enums.VoteCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Locale;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String choice;
    @Enumerated
    private VoteCategory category;
    private int voteTally;

    public VoteOption(String choice, VoteCategory category) {
        this.choice = choice;
        this.category = category;
        this.voteTally = 1;
    }

    public void addVote(){
        this.voteTally++;
    }
    public void removeVote(){
        this.voteTally--;
    }
}
