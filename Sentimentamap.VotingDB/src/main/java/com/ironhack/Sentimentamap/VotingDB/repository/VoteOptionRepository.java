package com.ironhack.Sentimentamap.VotingDB.repository;

import com.ironhack.Sentimentamap.VotingDB.dao.VoteOption;
import com.ironhack.Sentimentamap.VotingDB.dto.ResultDTO;
import com.ironhack.Sentimentamap.VotingDB.dto.TallyDTO;
import com.ironhack.Sentimentamap.VotingDB.enums.VoteCategory;
import feign.Param;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteOptionRepository extends JpaRepository<VoteOption, Long> {

    @Query("SELECT new com.ironhack.Sentimentamap.VotingDB.dto.ResultDTO(v.choice, v.voteTally) FROM VoteOption v " +
            "WHERE v.category = :category " +
            "ORDER BY v.voteTally")
    List<ResultDTO> getTop5ByCategory(@Param("category") VoteCategory category);

    Optional<VoteOption> findByChoiceAndCategory(@Param("choice") String choice, @Param("category") VoteCategory category);
}
