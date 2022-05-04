package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JsonTest
public class FilmTests {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Test
    void NameIsBlankTest(){
        Film film = new Film(" ", "description",
                LocalDate.parse("1995-12-27"), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void DescriptionIsMore200Test(){
        Film film = new Film("name", "descriptiondescriptiondescripti" +
                "ondescriptiondescriptiondescriptiondescriptiondescriptiondescripti" +
                "ondescriptiondescriptiondescriptiondescriptiondescriptiondescriptio" +
                "ndescriptiondescriptiondescription201",
                LocalDate.parse("1995-12-27"), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void DescriptionIs200Test(){
        Film film = new Film("name", "descriptiondescriptio" +
                "ndescriptiondescriptiondescriptiondescriptiondesc" +
                "riptiondescriptiondescriptiondescriptiondescriptiond" +
                "escriptiondescriptiondescriptiondescriptiondescriptiond" +
                "escriptiondescriptin200",
                LocalDate.parse("1995-12-27"), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    void DescriptionIsLess200Test(){
        Film film = new Film("name", "descriptiondescriptio" +
                "ndescriptiondescriptiondescriptiondescriptiondesc" +
                "riptiondescriptiondescriptiondescriptiondescriptiond" +
                "escriptiondescriptiondescriptiondescriptiondescriptiond" +
                "escriptiondescripti199",
                LocalDate.parse("1995-12-27"), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    void DataIsFailTest(){
        Film film = new Film("name", "description",
                LocalDate.parse("1895-12-27"), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void DataIsTrueTest(){
        Film film = new Film("name", "description",
                LocalDate.parse("1895-12-29"), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    void checkNameTest(){
        Film film2 = new Film(" ", "description",
                LocalDate.parse("1995-12-28"), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film2);
        assertFalse(violations.isEmpty());
        film2.toString();
    }

    @Test
    void durationIsLess0(){
        Film film = new Film("name", "description",
                LocalDate.parse("1995-12-29"), -10);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void durationIsMore0(){
        Film film = new Film("name", "description",
                LocalDate.parse("1995-12-29"), 1);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

}
