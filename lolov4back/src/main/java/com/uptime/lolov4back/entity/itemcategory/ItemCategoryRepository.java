package com.uptime.lolov4back.entity.itemcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Integer> {

    @Query("select i from ItemCategory i order by i.category.name")
    List<ItemCategory> findAllItemCategoriesSorted();

    @Query("select i from ItemCategory i where (i.item.channel.id = :channelId or 0 = :channelId)")
    List<ItemCategory> findCategoriesBy(Integer channelId);

}