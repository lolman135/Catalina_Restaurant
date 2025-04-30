package com.coursework.app.catalinarestaurant.utils.annotation;

import com.coursework.app.catalinarestaurant.utils.validation.FileTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileTypeValidator.class)
public @interface FileType {
    String message() default "Incorrect data type";
    String[] allowedTypes();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}