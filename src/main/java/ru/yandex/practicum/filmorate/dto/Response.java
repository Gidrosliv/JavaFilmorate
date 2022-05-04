package ru.yandex.practicum.filmorate.dto;

import lombok.Data;

@Data
public class Response {
    private String fieldName;
    private String message;

    public Response(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
