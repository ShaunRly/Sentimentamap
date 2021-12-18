package com.ironhack.Sentimentamap.MicroGeo.repository;

import com.ironhack.Sentimentamap.MicroGeo.dao.TrackedPlaceCords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackedPlaceCordRepository extends JpaRepository<TrackedPlaceCords, Long> {

    Optional<TrackedPlaceCords> findByPlaceName(String label);
}
