package com.coursework.app.catalinarestaurant.mapper.menuItem;

import com.coursework.app.catalinarestaurant.dto.menuItem.MenuItemDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
@Component
public class MenuItemMapperImpl implements MenuItemMapper {

    @Value("${app.upload.dir}")
    private String uploadPath;

    @Value("${app.upload.url.prefix}")
    private String uploadUrlPrefix;

    @Override
    public MenuItem toEntity(MenuItemDto request) throws IOException {
        MenuItem menuItem = new MenuItem(request.name(), request.description(), request.price(), request.category());

        MultipartFile multipartFile = request.file();
        String originalFileName = Objects.requireNonNull(multipartFile.getOriginalFilename());

        int dotIndex = originalFileName.lastIndexOf('.');
        String fileExtension = "";
        if (dotIndex >= 0) {
            fileExtension = originalFileName.substring(dotIndex);
        }

        String uniqueFileName = UUID.randomUUID().toString().concat(fileExtension);

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File destination = new File(uploadDir, uniqueFileName);
        multipartFile.transferTo(destination);

        String imageUrl = uploadUrlPrefix + "/" + uniqueFileName;
        menuItem.setImageUrl(imageUrl);
        return menuItem;
    }
}