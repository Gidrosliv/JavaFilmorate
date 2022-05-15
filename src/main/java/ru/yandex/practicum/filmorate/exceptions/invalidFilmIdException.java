package ru.yandex.practicum.filmorate.exceptions;

    public class invalidFilmIdException extends RuntimeException {
        public invalidFilmIdException(String message) {
            super(message);
        }
}
