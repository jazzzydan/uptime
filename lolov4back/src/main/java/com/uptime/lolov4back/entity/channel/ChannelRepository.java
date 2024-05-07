package com.uptime.lolov4back.entity.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {

    @Query("select c from Channel c where (c.id = :channelId or 0 = :channelId) order by c.title")
    List<Channel> getChannelsBy(Integer channelId);

    @Query("select c from Channel c where c.url = :channelURL")
    Channel getChannelBy(String channelURL);

    @Query("select (count(c) > 0) from Channel c where c.url = :channelURL")
    boolean channelExistsBy(String channelURL);

    @Query("select (count(c) > 0) from Channel c where upper(c.title) = upper(:channelTitle)")
    boolean channelExistsByTitle(String channelTitle);

}