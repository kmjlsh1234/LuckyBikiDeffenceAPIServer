package com.suhanlee.luckybikideffenceapiserver.shop.category.repository;

import com.suhanlee.luckybikideffenceapiserver.shop.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
