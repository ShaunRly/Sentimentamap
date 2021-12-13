package com.ironhack.SentimentamapDB.service;

import com.ironhack.SentimentamapDB.dao.TweetData;
import com.ironhack.SentimentamapDB.dto.BubbleDTO;
import com.ironhack.SentimentamapDB.dto.QueryDTO;
import com.ironhack.SentimentamapDB.dto.TweetDTO;
import com.ironhack.SentimentamapDB.repository.TweetDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService {

    @Autowired
    TweetDataRepository tweetDataRepository;

    public TweetData storeTweet(TweetDTO tweetDTO) {
        return tweetDataRepository.save(new TweetData(tweetDTO));
    }

    public List<TweetData> queryDB(QueryDTO queryDTO) {
        return tweetDataRepository.findTweets(
                queryDTO.getRule(),
                queryDTO.dateTimeStart,
                queryDTO.getDateTimeEnd()
        );
    }

    public List<TweetData> queryDBWithJPA(QueryDTO queryDTO){
        return tweetDataRepository.findByCreatedAtBetweenAndMatchingRulesContaining(
                queryDTO.dateTimeStart,
                queryDTO.getDateTimeEnd(),
                queryDTO.getRule()
        );
    }

    public List<BubbleDTO> getDataForBubble(QueryDTO queryDTO){
        return tweetDataRepository.findTweetsForBubble(
                queryDTO.getRule(),
                queryDTO.getDateTimeStart(),
                queryDTO.getDateTimeEnd()
        );
    }

    public List<TweetData> getDataForTopic(QueryDTO queryDTO) {
        return tweetDataRepository.findTweetsForTopic(
                queryDTO.getRule(),
                queryDTO.getDateTimeStart(),
                queryDTO.getDateTimeEnd()
        );
    }

    public String getGeoJsonData() {
        return null;
    }

    public List<String> getTrackedTopics(QueryDTO queryDTO) {
        return tweetDataRepository.findTrackedTopicsPerHalfHour(queryDTO.dateTimeStart, queryDTO.dateTimeEnd);
    }

}
