package com.ironhack.Sentimentamap.TwitterAPIMS.Proxy;

import com.ironhack.Sentimentamap.TwitterAPIMS.dto.TweetDTO;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@EnableEurekaClient
public interface SentimentProxy {

    @PostMapping("/api/v1/postTweet")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postTweetsForSentimentAnalysis(@RequestBody TweetDTO tweetDTO);
}
