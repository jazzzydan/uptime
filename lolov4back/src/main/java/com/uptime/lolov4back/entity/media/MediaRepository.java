package com.uptime.lolov4back.entity.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Integer> {


    @Query("select m from Media m where m.item.id = :itemId")
    Optional<Media> findMediaBy(Integer itemId);
}