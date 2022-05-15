package ru.yandex.practicum.filmorate.exceptions;

public class InvalidCountException extends RuntimeException  {
    public InvalidCountException(String message) {
        super(message);
    }
}
