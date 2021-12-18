package com.ironhack.Sentimentamap.VotingDB.dto;

import com.ironhack.Sentimentamap.VotingDB.enums.VoteCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

    private String choice;
    private VoteCategory category;
}
