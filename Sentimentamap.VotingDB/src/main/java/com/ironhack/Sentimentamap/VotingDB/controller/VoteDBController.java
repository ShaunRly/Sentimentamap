package com.ironhack.Sentimentamap.VotingDB.controller;

import com.ironhack.Sentimentamap.VotingDB.dao.VoteOption;
import com.ironhack.Sentimentamap.VotingDB.dto.CategoryOptionsDTO;
import com.ironhack.Sentimentamap.VotingDB.dto.ResultDTO;
import com.ironhack.Sentimentamap.VotingDB.dto.TallyDTO;
import com.ironhack.Sentimentamap.VotingDB.dto.VoteDTO;
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
    public List<TallyDTO> getTalliesForCategories(){
        return voteService.getTalliesForCategories();
    }

    @GetMapping("/{category}")
    public List<ResultDTO> getTalliesForCategory(@PathVariable String category){
        return voteService.getTalliesForCategory(category);
    }

    @PostMapping("/postoptionset")
    public List<VoteOption> postNewVoteOptionSet(@RequestBody CategoryOptionsDTO categoryOptionsDTO){
        return voteService.postNewVoteOptionSet(categoryOptionsDTO);
    }

    @PostMapping("/vote")
    public VoteOption castVote(@RequestBody VoteDTO voteDTO){
        return voteService.castVote(voteDTO.getChoice(), voteDTO.getCategory());
    }

    @DeleteMapping("/{choice}")
    public VoteOption removeVote(@RequestBody VoteDTO voteDTO){
        return voteService.removeVote(voteDTO.getChoice(), voteDTO.getCategory());
    }

    @DeleteMapping("/newdayclear")
    public void deleteForNewDay(){
        voteRepository.deleteAll();
    }

}
