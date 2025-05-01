package com.coursework.app.catalinarestaurant.service;

import java.util.List;

public interface BaseService<T> {
    boolean deleteById(Long id);
    List<T> findAll();
    T findById(Long id);
}