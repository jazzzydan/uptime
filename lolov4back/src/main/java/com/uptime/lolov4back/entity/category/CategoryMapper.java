package com.uptime.lolov4back.entity.category;

import com.uptime.lolov4back.endpoint.dto.CategoryInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    @Mapping(source = "id", target = "categoryId")
    @Mapping(source = "name", target = "categoryName")
    CategoryInfo toCategoryInfo(Category category);

    List<CategoryInfo> toCategoryInfos(List<Category> category);

}