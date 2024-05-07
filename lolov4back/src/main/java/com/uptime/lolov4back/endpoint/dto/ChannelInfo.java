package com.uptime.lolov4back.endpoint.dto;

import com.uptime.lolov4back.entity.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Channel}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelInfo implements Serializable {

    private Integer channelId;
    private String channelTitle;
    private String channelURL;
}