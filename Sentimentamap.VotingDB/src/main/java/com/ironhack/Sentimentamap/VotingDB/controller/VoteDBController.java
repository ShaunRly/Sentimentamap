package com.ironhack.Sentimentamap.VotingDB.controller;

import com.ironhack.Sentimentamap.VotingDB.dao.VoteOption;
import com.ironhack.Sentimentamap.VotingDB.dto.CategoryOptionsDTO;
import com.ironhack.Sentimentamap.VotingDB.dto.TallyDTO;
import com.ironhack.Sentimentamap.VotingDB.repository.VoteOptionRepository;
import com.ironhack.Sentimentamap.VotingDB.service.VoteOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voteDB")
public class VoteDBController {

    @Autowired
    VoteOptionRepository voteRepository;

    @Autowired
    VoteOptionService voteService;

    @GetMapping("/tallies")
    public List<TallyDTO> getTalliesForCategories(@RequestParam String date){
        return voteService.getTalliesForCategories(date);
    }

    @PostMapping("/postoptionset")
    public List<VoteOption> postNewVoteOptionSet(@RequestBody CategoryOptionsDTO categoryOptionsDTO){
        return voteService.postNewVoteOptionSet(categoryOptionsDTO);
    }

    @PostMapping("/vote")
    public VoteOption castVote(@RequestParam String choice, @RequestParam String category){
        return voteService.castVote(choice, category);
    }

    @DeleteMapping("/{choice}")
    public VoteOption removeVote(@RequestParam String choice, @RequestParam String category){
        return voteService.removeVote(choice, category);
    }

    @DeleteMapping("/newdayclear")
    public void deleteForNewDay(){
        voteRepository.deleteAll();
    }

}
