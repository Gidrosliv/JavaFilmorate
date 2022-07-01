package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserDbStorageTest {
    private final UserDbStorage userDbStorage;
    private User user;

    @BeforeEach
    public void createUserTests() {
        user = new User("gidrosliv", "denis", "deneshua@mail.ru",
                LocalDate.of(1989, 06, 19));
    }

    @Test
    void returnUserWhenDbHasUser() {
        Map<Integer, User> users = userDbStorage.getAllUsers();
        assertThat(users).hasSize(1);
        User testUser = users.get(1);
        assertThat(testUser.getName()).isEqualTo("name");
        assertThat(testUser.getLogin()).isEqualTo("login");
        assertThat(testUser.getEmail()).isEqualTo("mail");
        assertThat(testUser.getBirthday()).isEqualTo("1989-06-19");
    }

    @Test
    void addUser() {
        userDbStorage.create(user);
        Map<Integer, User> users = userDbStorage.getAllUsers();
        User testUser = users.get(2);
        assertThat(testUser.getName()).isEqualTo(user.getName());
        assertThat(testUser.getLogin()).isEqualTo(user.getLogin());
        assertThat(testUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(testUser.getBirthday()).isEqualTo(user.getBirthday());
    }

    @Test
    void updateUser() {
        User updateUser = new User(1, "newLogin", "newName", "newEmail",
                LocalDate.of(2020, 01, 01));
        userDbStorage.update(updateUser);
        User returnedUser = userDbStorage.getAllUsers().get(1);
        assertThat(returnedUser).isEqualTo(updateUser);
    }

    @Test
    void removeUser() {
        assertThat(userDbStorage.getAllUsers().get(1).getId()).isEqualTo(1);
        assertThat(userDbStorage.getAllUsers()).hasSize(1);
        userDbStorage.delete(1);
        assertThat(userDbStorage.getAllUsers()).isEmpty();
    }

    @Test
    void returnUserById() {
        assertThat(userDbStorage.getUser(1).getEmail()).isEqualTo("mail");
    }
}
