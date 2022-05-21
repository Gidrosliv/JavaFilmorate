package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmDbStorageTest {
    private final FilmDbStorage filmDbStorage;
    private Film film1;

    @BeforeEach
    public void createFilmsTests() {
        film1 = new Film(2, "The Rock2", "Starring Nicolas Cage and Sean Connery2",
                LocalDate.of(1994, 6, 7), 135, new Mpa(3));
    }


    @Test
    void createFilmTest() {
        filmDbStorage.create(film1);
        assertThat(filmDbStorage.getFilm(2).getDescription()).isEqualTo(film1.getDescription());
    }

    @Test
    void getFilmTest() {
        Film films = filmDbStorage.getFilm(1);
        assertThat(films.getId()).isEqualTo(1);
        assertThat(films.getName()).isEqualTo("The Batman");
        assertThat(films.getDescription()).isEqualTo("Description like description");
        assertThat(films.getReleaseDate()).isEqualTo("1990-06-06");
        assertThat(films.getDuration()).isEqualTo(131);
        assertThat(films.getMpa().getId()).isEqualTo(1);
    }

    @Test
    void removeFilmTest() {
        filmDbStorage.delete(film1);
        assertThat(filmDbStorage.list()).hasSize(1);
    }

}
