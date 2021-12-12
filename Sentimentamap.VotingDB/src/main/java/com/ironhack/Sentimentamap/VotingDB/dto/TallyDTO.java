package com.ironhack.Sentimentamap.VotingDB.dto;

import com.ironhack.Sentimentamap.VotingDB.enums.VoteCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TallyDTO {

    private VoteCategory category;
    private List<ResultDTO> results;
}
