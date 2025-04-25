package com.coursework.app.catalinarestaurant.utils.validation;

import com.coursework.app.catalinarestaurant.utils.annotation.FileType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {

    private List<String> allowedTypes;

    @Override
    public void initialize(FileType constraintAnnotation) {
        allowedTypes = List.of(constraintAnnotation.allowedTypes());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile == null || allowedTypes.contains(multipartFile.getContentType());
    }
}
