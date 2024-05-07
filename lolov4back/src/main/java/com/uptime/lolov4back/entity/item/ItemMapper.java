package com.uptime.lolov4back.entity.item;

import com.uptime.lolov4back.endpoint.dto.ItemInfo;
import com.uptime.lolov4back.util.DateConverter;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    @Mapping(source = "channel.id", target = "channelId")
    @Mapping(source = "channel.title", target = "channelTitle")
    @Mapping(source = "id", target = "itemId")
    @Mapping(source = "title", target = "itemTitle")
    @Mapping(source = "link", target = "itemLink")
    @Mapping(source = "pubDate", target = "itemPubDate")
    @Mapping(source = "description", target = "itemDescription")
    @Mapping(source = "source", target = "itemSource")
    @Mapping(source = "author", target = "itemAuthor")
    ItemInfo toItemInfo(Item item);

    List<ItemInfo> toItemInfos(List<Item> item);

//    @Named("localDateTimeToString")
//    static String localDateTimeToString(LocalDateTime date) {
//        return DateConverter.convertToString(date);
//    }
}