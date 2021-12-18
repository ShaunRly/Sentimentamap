package com.ironhack.Sentimentamap.VotingDB.service;

import com.ironhack.Sentimentamap.VotingDB.dao.VoteOption;
import com.ironhack.Sentimentamap.VotingDB.dto.CategoryOptionsDTO;
import com.ironhack.Sentimentamap.VotingDB.dto.ResultDTO;
import com.ironhack.Sentimentamap.VotingDB.dto.TallyDTO;
import com.ironhack.Sentimentamap.VotingDB.enums.VoteCategory;
import com.ironhack.Sentimentamap.VotingDB.repository.VoteOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class VoteOptionService {

    @Autowired
    VoteOptionRepository voteRepository;

    public List<TallyDTO> getTalliesForCategories() {
        List<TallyDTO> tallyDTOS = new ArrayList<>();
        for (var category : VoteCategory.values()){
            List<ResultDTO> resultDTOS = voteRepository.getTop5ByCategory(category);
            tallyDTOS.add(new TallyDTO(category, resultDTOS));
        }
        return tallyDTOS;
    }



    public List<VoteOption> postNewVoteOptionSet(CategoryOptionsDTO categoryOptionsDTO) {
        List<VoteOption> voteOptionsList = new ArrayList<>();
        System.out.println(categoryOptionsDTO.getCategory());
        for(var voteOption : categoryOptionsDTO.getVoteOptions()){
            voteOptionsList.add(new VoteOption(voteOption, categoryOptionsDTO.getCategory()));
        }
        return voteRepository.saveAll(voteOptionsList);
    }

    public VoteOption castVote(String choice, VoteCategory category) {
        Optional<VoteOption> foundVote = voteRepository.findByChoiceAndCategory(choice, category);
        if(foundVote.isPresent()){
            foundVote.get().addVote();
            return voteRepository.save(foundVote.get());
        }
        else{
            return voteRepository.save(new VoteOption(choice, category));
        }
    }

    public VoteOption removeVote(String choice, VoteCategory category) {
        Optional<VoteOption> foundVote = voteRepository.findByChoiceAndCategory(choice, category);
        if(foundVote.isPresent()){
            foundVote.get().removeVote();
            return voteRepository.save(foundVote.get());
        }
        else{
            throw new NoSuchElementException("No Vote Option Found with that Name");
        }
    }

    public List<ResultDTO> getTalliesForCategory(String category) {
        List<ResultDTO> resultDTOS = voteRepository.getTop5ByCategory(VoteCategory.valueOf(category));
        return resultDTOS;
    }
}
