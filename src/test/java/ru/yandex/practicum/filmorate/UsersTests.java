package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JsonTest
public class UsersTests {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void NameIsNotBlankTest() {
        User user = new User("login", "name", "kinogavno@gmail.com",
                LocalDate.parse("1995-12-27"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void NameIsOnlyBlankTest() {
        User user = new User("login", " ", "kino@gmail.com",
                LocalDate.parse("1995-12-27"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
        assertEquals("login", user.getName());
    }

    @Test
    void loginIsBlankTest() {
        User user = new User("", "name", "kino@gmail.com",
                LocalDate.parse("1995-12-27"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void emailIsEmptyTest() {
        User user = new User("login", "name", " ",
                LocalDate.parse("1995-12-27"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void emailIsContainsSpacesTest() {
        User user = new User("login", "name", "kino @gmail.com",
                LocalDate.parse("1995-12-27"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void emailIsTrueTest() {
        User user = new User("login", "name", "kino@gmail.com",
                LocalDate.parse("1995-12-27"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void birthdayInFuture() {
        User user = new User("login", "name", "kino@gmail.com",
                LocalDate.parse("3000-12-27"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }
}
