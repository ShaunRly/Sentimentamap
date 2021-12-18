package com.ironhack.Sentimentamap.VotingDB.dto;

import com.ironhack.Sentimentamap.VotingDB.enums.VoteCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryOptionsDTO {

    private VoteCategory category;
    private List<String> voteOptions;

    public CategoryOptionsDTO(String category, List<String> voteOptions) {
        this.category = VoteCategory.valueOf(category.toUpperCase(Locale.ROOT));
        this.voteOptions = voteOptions;
    }
}
