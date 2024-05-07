package com.uptime.lolov4back.endpoint.dto;

import com.uptime.lolov4back.entity.media.Media;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Media}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaInfo implements Serializable {
    private Integer itemId;
    private String url;
    private String medium;
}