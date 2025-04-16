package com.coursework.app.catalinarestaurant.repository.orderview;

import com.coursework.app.catalinarestaurant.entity.CanceledOrderView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanceledOrderViewRepository extends JpaRepository<CanceledOrderView, Long> {
}
