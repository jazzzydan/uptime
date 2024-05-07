package com.uptime.lolov4back.entity.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {



    @Query("select i from Item i where i.guid = :guid")
    Item getByGuid(@Param("guid") String guid);

    @Query("select i from Item i where (i.channel.id = :channelId or 0 = :channelId) order by i.pubDate DESC")
    List<Item> findItemsBy(Integer channelId);
}