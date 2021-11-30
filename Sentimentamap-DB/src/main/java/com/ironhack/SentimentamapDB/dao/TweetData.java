package com.ironhack.SentimentamapDB.dao;

import com.ironhack.SentimentamapDB.dto.TweetDTO;
import com.ironhack.SentimentamapDB.model.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private List<String> matchingRules;

    public TweetData(TweetDTO tweetDTO) {
        this.sentiment = tweetDTO.getSentiment();
        this.coordinates = tweetDTO.getCoordinates();
        this.createdAt = LocalDateTime.parse(tweetDTO.getCreatedAt() + " 12:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.matchingRules = tweetDTO.getMatchingRules();
    }

}
