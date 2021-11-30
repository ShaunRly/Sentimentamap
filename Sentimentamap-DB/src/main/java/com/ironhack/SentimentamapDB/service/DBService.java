package com.ironhack.SentimentamapDB.service;

import com.ironhack.SentimentamapDB.dao.TweetData;
import com.ironhack.SentimentamapDB.dto.TweetDTO;
import com.ironhack.SentimentamapDB.repository.TweetDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    TweetDataRepository tweetDataRepository;

    public void storeTweet(TweetDTO tweetDTO) {
        tweetDataRepository.save(new TweetData(tweetDTO));
    }
}
