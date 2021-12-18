package com.ironhack.Sentimentamap.VotingDB.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

    private String optionName;
    private int voteTally;
}
