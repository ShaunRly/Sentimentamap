package com.ironhack.SentimentamapDB.controller;

import com.ironhack.SentimentamapDB.dao.TweetData;
import com.ironhack.SentimentamapDB.dto.TweetDTO;
import com.ironhack.SentimentamapDB.repository.TweetDataRepository;
import com.ironhack.SentimentamapDB.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tweetdb")
public class DB_Controller {

    @Autowired
    DBService dbService;

    @Autowired
    TweetDataRepository tweetDataRepository;

    @PostMapping
    @RequestMapping("/post")
    public void storeTweet(@RequestBody TweetDTO tweetDTO){
        dbService.storeTweet(tweetDTO);
    }

    @GetMapping("/{id}")
    public TweetData getTweet(@PathVariable Long id){
        return tweetDataRepository.findById(id).orElse(null);
    }
}
