package com.coursework.app.catalinarestaurant.repository.menuItem;

import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Modifying
    @Query("UPDATE MenuItem m SET m.name = :name WHERE m.id = :id")
    void updateNameById(@Param("id") Long id, @Param("name")String name);

    @Modifying
    @Query("UPDATE MenuItem m SET m.description = :description WHERE m.id = :id")
    void updateDescriptionById(@Param("id") Long id, @Param("description")String description);

    @Modifying
    @Query("UPDATE MenuItem m SET m.price = :price WHERE m.id = :id")
    void updatePriceById(@Param("id") Long id, @Param("price")double price);

    @Modifying
    @Query("UPDATE MenuItem m SET m.category = :category WHERE m.id = :id")
    void updateCategoryById(@Param("id") Long id, @Param("category") Category category);
}
