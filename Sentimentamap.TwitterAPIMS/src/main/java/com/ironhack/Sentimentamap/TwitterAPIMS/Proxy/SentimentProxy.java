package com.ironhack.Sentimentamap.TwitterAPIMS.Proxy;

import com.ironhack.Sentimentamap.TwitterAPIMS.dto.TweetDTO;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "vader-service")
public interface SentimentProxy {

    @PostMapping("/analyze")
    void postTweetsForSentimentAnalysis(@RequestBody TweetDTO tweetDTO);
}
