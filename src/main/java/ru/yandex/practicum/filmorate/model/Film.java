package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.annotation.CheckData;

import javax.validation.constraints.*;
import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.controllers.FilmController.filmIdGenerator;


@Data
@Validated
public class Film {
    private int id;
    @NotBlank(message = "name cannot be empty")
    @NotNull(message = "name cannot be null")
    private String name;
    @Size(max = 200)
    @NotBlank(message = "name cannot be empty")
    @NotNull
    private String description;
    @NotNull
    @CheckData
    private LocalDate releaseDate;
    @Positive(message = "must be greater than 0")
    @NotNull
    private int duration;

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.id = filmIdGenerator();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;

    }
}

