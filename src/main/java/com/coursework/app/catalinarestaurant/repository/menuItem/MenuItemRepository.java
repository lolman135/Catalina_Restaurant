package com.coursework.app.catalinarestaurant.repository.menuItem;

import com.coursework.app.catalinarestaurant.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("MenuItemRepository")
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
