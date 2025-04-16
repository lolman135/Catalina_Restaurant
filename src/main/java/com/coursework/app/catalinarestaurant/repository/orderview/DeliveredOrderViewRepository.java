package com.coursework.app.catalinarestaurant.repository.orderview;

import com.coursework.app.catalinarestaurant.entity.DeliveredOrderView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveredOrderViewRepository extends JpaRepository<DeliveredOrderView, Long> {
}
