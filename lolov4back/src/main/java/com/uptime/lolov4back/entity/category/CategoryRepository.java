package com.uptime.lolov4back.entity.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c order by c.name")
    List<Category> getCategoriesBy();

    @Query("select (count(c) > 0) from Category c where upper(c.name) = upper(:categoryName)")
    boolean categoryExistsBy(String categoryName);

    @Query("select c from Category c where upper(c.name) = upper(:categoryName)")
    Category getCategoryBy(String categoryName);

}