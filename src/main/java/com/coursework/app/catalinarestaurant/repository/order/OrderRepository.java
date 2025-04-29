package com.coursework.app.catalinarestaurant.repository.order;

import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("UPDATE Order o SET o.orderStatus = :orderStatus WHERE o.id = :id")
    void updateOrOrderStatusById(@Param("id") Long id, @Param("orderStatus")OrderStatus orderStatus);
}
