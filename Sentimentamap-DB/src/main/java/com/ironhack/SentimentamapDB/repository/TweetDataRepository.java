package com.ironhack.SentimentamapDB.repository;

import com.ironhack.SentimentamapDB.dao.TweetData;
import com.ironhack.SentimentamapDB.dto.BubbleDTO;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TweetDataRepository extends JpaRepository<TweetData, Long> {

    //Not sure you can use between combined with and like this
    List<TweetData> findByCreatedAtBetweenAndMatchingRulesContaining(LocalDateTime date1, LocalDateTime date2, String matchingRule);

    @Query("SELECT t FROM TweetData t " +
            "WHERE t.matchingRules like ?1 " +
            "AND t.createdAt BETWEEN ?2 AND ?3 ")
    List<TweetData> findTweets(String rule, LocalDateTime date1, LocalDateTime date2);

    @Query("SELECT new com.ironhack.SentimentamapDB.dto.BubbleDTO(COUNT(t), AVG(t.sentiment.compound), t.matchingRules) FROM TweetData t " +
            "WHERE t.matchingRules LIKE %:rule% " +
            "AND t.createdAt BETWEEN :date1 AND :date2 " +
            "AND t.sentiment.compound != 0" +
            "GROUP BY t.matchingRules ")
    List<BubbleDTO> findTweetsForBubble(@Param("rule") String rule,@Param("date1") LocalDateTime date1,@Param("date2") LocalDateTime date2);
}
