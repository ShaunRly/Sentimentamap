package com.ironhack.Sentimentamap.TwitterAPIMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TweetDTO {

    private String tweetText;
    private LocalDateTime createdAt;
    private String coordinates;
    private List<String> matchingRules;

    @Override
    public String toString() {
        return "TweetDTO{" +
                "tweetText='" + tweetText + '\'' +
                ", createdAt=" + createdAt +
                ", coordinates='" + coordinates + '\'' +
                ", matchingRules=" + matchingRules +
                '}';
    }
}
