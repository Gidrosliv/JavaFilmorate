package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.dto.Response;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationException extends Exception {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity handleIndividualEmailException(ConstraintViolationException e) {
        final List<Response> violations = e.getConstraintViolations().stream()
                .map(violation -> new Response(violation.getPropertyPath().toString(),violation.getMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(violations, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handleUserAlreadyException(MethodArgumentNotValidException e) {
        final List<Response> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Response(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(violations, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}