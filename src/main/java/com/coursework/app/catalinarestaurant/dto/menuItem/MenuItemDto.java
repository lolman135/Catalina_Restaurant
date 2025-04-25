package com.coursework.app.catalinarestaurant.dto.menuItem;

import com.coursework.app.catalinarestaurant.enums.Category;
import com.coursework.app.catalinarestaurant.utils.annotation.FileType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

public record MenuItemDto(
        @Pattern(
                regexp = "^[А-Яа-яA-Za-zЁёІіЇїЪъЫы\\-\\s]{0,50}+$",
                message = "Name should contains only letters"
        )
        String name,

        @NotBlank(message = "Description should not be empty")
        String description,

        @Min(0)
        double price,

        @NotNull
        Category category,

        @FileType(allowedTypes = {"image/jpeg", "image/png"})
        MultipartFile file)
{}
