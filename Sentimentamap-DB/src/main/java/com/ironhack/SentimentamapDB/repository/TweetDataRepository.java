package com.ironhack.SentimentamapDB.repository;

import com.ironhack.SentimentamapDB.dao.TweetData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TweetDataRepository extends JpaRepository<TweetData, Long> {

    //Not sure you can use between combined with and like this
    List<TweetData> findByCreatedAtBetweenAndMatchingRulesEquals(LocalDateTime date1, LocalDateTime date2, String matchingRule);

    @Query("SELECT t FROM TweetData t " +
            "WHERE t.matchingRules = ?1 AND" +
            "t.createdAt BETWEEN ?2 AND ?3")
    List<TweetData> findTweets(String rule, LocalDateTime date1, LocalDateTime date2);
}
