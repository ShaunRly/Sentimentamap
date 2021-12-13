package com.ironhack.SentimentamapDB.controller;

import com.ironhack.SentimentamapDB.dao.TweetData;
import com.ironhack.SentimentamapDB.dto.BubbleDTO;
import com.ironhack.SentimentamapDB.dto.QueryDTO;
import com.ironhack.SentimentamapDB.dto.TweetDTO;
import com.ironhack.SentimentamapDB.repository.TweetDataRepository;
import com.ironhack.SentimentamapDB.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tweetdb")
public class DB_Controller {

    @Autowired
    DBService dbService;

    @Autowired
    TweetDataRepository tweetDataRepository;

    @PostMapping
    @RequestMapping("/post")
    public TweetData storeTweet(@RequestBody TweetDTO tweetDTO){
        return dbService.storeTweet(tweetDTO);
    }

    @GetMapping("/{id}")
    public TweetData getTweet(@PathVariable Long id){
        return tweetDataRepository.findById(id).orElse(null);
    }

    @GetMapping("/query")
    public List<TweetData> getTweetsByQuery(@RequestBody QueryDTO queryDTO){
        return dbService.queryDB(queryDTO);
    }


    @GetMapping("/bubbleCategoryQuery")
    public List<BubbleDTO> getTweetsForCategoryForBubble(@RequestParam String rule, @RequestParam String dateTimeStart, @RequestParam String dateTimeEnd){
        return dbService.getDataForBubble(new QueryDTO(rule, dateTimeStart, dateTimeEnd));
    }

    @GetMapping("/topicQuery")
    public List<TweetData> getTweetsForTopic(@RequestParam String rule, @RequestParam String dateTimeStart, @RequestParam String dateTimeEnd){
        return dbService.getDataForTopic(new QueryDTO(rule, dateTimeStart, dateTimeEnd));
    }

    @GetMapping("/mapdata")
    public String getGeoJsonData(){
        return dbService.getGeoJsonData();
    }

    @GetMapping("/tracked")
    public List<String> getTrackedTopics(@RequestParam String dateTimeStart, @RequestParam String dateTimeEnd){
        return dbService.getTrackedTopics(new QueryDTO(dateTimeStart, dateTimeEnd));
    }
}
