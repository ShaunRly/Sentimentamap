package com.ironhack.SentimentamapDB.dto;

import com.ironhack.SentimentamapDB.model.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TweetDTO {

    private Sentiment sentiment;
    private String createdAt;
    private String coordinates;
    private List<String> matchingRules;

    @Override
    public String toString() {
        return "TweetDTO{" +
                "sentiment=" + sentiment +
                ", createdAt='" + createdAt + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", matchingRules='" + matchingRules + '\'' +
                '}';
    }
}
