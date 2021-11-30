package com.ironhack.Sentimentamap.TwitterAPIMS.Service;

import com.ironhack.Sentimentamap.TwitterAPIMS.Proxy.SentimentProxy;
import com.ironhack.Sentimentamap.TwitterAPIMS.dto.TweetDTO;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyService {

    @Autowired
    private SentimentProxy sentimentProxy;

    public void sendTweetToSentimentAnalysis(TweetDTO tweetDTO){
        sentimentProxy.postTweetsForSentimentAnalysis(tweetDTO);
    }
}
