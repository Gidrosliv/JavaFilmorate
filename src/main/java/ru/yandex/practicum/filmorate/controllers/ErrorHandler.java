package ru.yandex.practicum.filmorate.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.InvalidCountException;
import ru.yandex.practicum.filmorate.exceptions.invalidFilmIdException;
import ru.yandex.practicum.filmorate.exceptions.InvalidUserIdException;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@RestControllerAdvice(assignableTypes = {UserController.class, FilmController.class})
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> DataIsNotValid(final MethodArgumentNotValidException e) {
        return Map.of("error", "invalid user data");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> DataIsNotValid(final ConstraintViolationException e) {
        return Map.of("error", "invalid user data");
    }

    @ExceptionHandler(InvalidUserIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> userIdIsNotValid(final InvalidUserIdException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(invalidFilmIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> filmIdIsNotValid(final invalidFilmIdException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(InvalidCountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> countDataIsNotValid(final InvalidCountException e) {
        return Map.of("error", "invalid count data");
    }

}

