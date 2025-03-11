package com.coursework.app.catalinarestaurant.repository;

import com.coursework.app.catalinarestaurant.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("MenuItemRepository")
public interface MenuItemsRepository extends JpaRepository<MenuItem, Long> {
}
