package com.uptime.lolov4back.endpoint.dto;

import com.uptime.lolov4back.entity.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Category}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfo implements Serializable {

    private Integer categoryId;
    private String categoryName;
}