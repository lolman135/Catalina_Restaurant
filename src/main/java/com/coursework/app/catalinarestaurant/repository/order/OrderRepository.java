package com.coursework.app.catalinarestaurant.repository.order;

import com.coursework.app.catalinarestaurant.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("OrderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {
}
