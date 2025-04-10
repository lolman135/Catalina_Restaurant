package com.coursework.app.catalinarestaurant.utils.stringGenerator;

import com.coursework.app.catalinarestaurant.entity.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartInfoGenerator {

    public static List<String> getInfo(Map<MenuItem, Integer> map){
        List<String> strings = new ArrayList<>();
        map.forEach((item, quantity) -> {
            StringBuilder tempStringBuilder = new StringBuilder();
            strings.add(tempStringBuilder
                    .append("<b>" + item.getName() + "</b>")
                    .append(": x")
                    .append(quantity)
                    .toString());
        });
        return strings;
    }
}
