package com.coursework.app.catalinarestaurant.service;

import java.util.List;

public interface BaseViewService<T, ID> {
    List<T> findAll();
    T findById(ID id);
}