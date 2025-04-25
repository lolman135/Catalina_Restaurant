package com.coursework.app.catalinarestaurant.entity;

import com.coursework.app.catalinarestaurant.enums.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_items")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    public MenuItem(String name, String description, double price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}