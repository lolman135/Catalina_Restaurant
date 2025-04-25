package com.coursework.app.catalinarestaurant.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OrderDto(
        @Pattern(
                regexp = "^[А-Яа-яA-Za-zЁёІіЇїЪъЫы\\-\\s]{2,50}+$",
                message = "Name should contains only letters and be at least 2 symbols long"
        )
        String customerName,

        @Pattern(regexp = "^\\+380\\d{9}$", message = "Valid phone number: +380XXXXXXXXX")
        String customerPhone,

        @NotBlank(message = "Address shouldn't be empty")
        @Size(min = 10, max = 255, message = "Address should be at least 10 symbols long")
        String customerAddress)
{}