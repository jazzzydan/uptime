package com.uptime.lolov4back.endpoint.dto;

import com.uptime.lolov4back.entity.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Item}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfo implements Serializable {

    private Integer channelId;
    private String channelTitle;

    private Integer itemId;
    private String itemTitle;
    private String itemLink;
    private LocalDateTime itemPubDate;
    private String itemDescription;
    private String itemSource;
    private String itemAuthor;

    private List<CategoryInfo> categoryInfos;
    private MediaInfo mediaInfo;
}