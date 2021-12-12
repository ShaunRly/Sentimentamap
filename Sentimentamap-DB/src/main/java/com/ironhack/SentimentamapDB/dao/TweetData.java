package com.ironhack.SentimentamapDB.dao;

import com.ironhack.SentimentamapDB.dto.TweetDTO;
import com.ironhack.SentimentamapDB.model.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TweetData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private Sentiment sentiment;
    private LocalDateTime createdAt;
    private String coordinates;
    private String matchingRules;

    public TweetData(TweetDTO tweetDTO) {
        this.sentiment = tweetDTO.getSentiment();
        this.coordinates = tweetDTO.getCoordinates();
        this.createdAt = LocalDateTime.parse(tweetDTO.getCreatedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.matchingRules = tweetDTO.getMatchingRules();
    }

}
