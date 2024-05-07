package com.uptime.lolov4back.entity.channel;

import com.uptime.lolov4back.endpoint.dto.ChannelInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChannelMapper {

    @Mapping(source = "id", target = "channelId")
    @Mapping(source = "url", target = "channelURL")
    @Mapping(source = "title", target = "channelTitle")
    ChannelInfo channelInfo(Channel channel);

    List<ChannelInfo> toChannelInfos(List<Channel> channel);
}