package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.annotation.CheckData;
import ru.yandex.practicum.filmorate.controllers.FilmController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@Validated
public class Film implements Entities {
    private int id;
    @NonNull
    @NotBlank(message = "cannot be empty")
    private String name;
    @Size(max = 200)
    @NonNull
    @NotBlank(message = "cannot be empty")
    private String description;
    @CheckData
    private LocalDate releaseDate;
    @Positive(message = "must be greater than 0")
    private int duration;

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.id = FilmController.idGenerator();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;

    }
}

