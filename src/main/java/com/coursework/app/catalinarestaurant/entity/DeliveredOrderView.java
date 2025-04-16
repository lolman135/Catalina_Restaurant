package com.coursework.app.catalinarestaurant.entity;

import com.coursework.app.catalinarestaurant.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "delivered_orders")
public class DeliveredOrderView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "created_at")
    private LocalTime createdAt;

}
