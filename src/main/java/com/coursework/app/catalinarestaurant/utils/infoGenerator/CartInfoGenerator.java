package com.coursework.app.catalinarestaurant.utils.infoGenerator;

import com.coursework.app.catalinarestaurant.entity.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartInfoGenerator {

    public static List<String> getDishInfo(Map<MenuItem, Integer> map){
        List<String> strings = new ArrayList<>();
        map.forEach((item, quantity) -> {
            StringBuilder tempStringBuilder = new StringBuilder();
            strings.add(tempStringBuilder
                    .append("<b>")
                    .append(item.getName())
                    .append(":</b> ")
                    .append(item.getPrice())
                    .append(" ×")
                    .append(quantity)
                    .toString());
        });
        return strings;
    }

    public static double getTotalPriceInfo(Map<MenuItem, Integer> map){
         return  map.entrySet().stream()
                .mapToDouble(
                        entry -> entry.getKey()
                                .getPrice() * entry.getValue()).sum();
    }
}
