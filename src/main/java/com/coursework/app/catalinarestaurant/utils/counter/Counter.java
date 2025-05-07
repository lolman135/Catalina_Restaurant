package com.coursework.app.catalinarestaurant.utils.counter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Counter {

    public static <T> Map<Integer, T> getMapOfItemNumbers(List<T> items) {
        Map<Integer, T> map = new LinkedHashMap<>();
        for (int i = 0; i < items.size(); i++) {
            map.put(i + 1, items.get(i));
        }
        return map;
    }
}
