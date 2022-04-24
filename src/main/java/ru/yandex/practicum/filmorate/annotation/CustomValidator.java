package ru.yandex.practicum.filmorate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class CustomValidator implements ConstraintValidator<CheckData, LocalDate> {

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext context) {
        if (releaseDate.isAfter(LocalDate.of(1895, 12, 28))) {
            return true;
        }
        return false;
    }

}