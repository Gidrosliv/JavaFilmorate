# Приложение для поиска и хранения фильмов. Java-filmorate.
#### Учебный проект
    
    
[![Films API Tests](https://github.com/Gidrosliv/javafilmorate/actions/workflows/api-tests.yml/badge.svg)](https://github.com/Gidrosliv/javafilmorate/actions/workflows/api-tests.yml)
        
Описание:
Бэкенд для сервиса, который будет работать с фильмами и оценками пользователей, а также возвращать топ фильмов, рекомендованных к просмотру.
    
![Java](https://img.shields.io/badge/-Java-green) ![Spring](https://img.shields.io/badge/-Spring-blue)![Postgres SQL](https://img.shields.io/badge/-Postgres%20SQL-brightgreen) ![H2](https://img.shields.io/badge/-H2-green) ![Maven](https://img.shields.io/badge/-Maven-yellowgreen) ![Lombok](https://img.shields.io/badge/-Lombok-lightgrey) ![JDBC](https://img.shields.io/badge/-JDBC-green) ![Git](https://badgen.net/badge/icon/github?icon=github&label)

Функциональность:
- добавление и хранение данных в памяти;
- добавление и хранение данных в файле;
- добавление и хранение дыных в базе данных;
- доступ к методам менеджера через HTTP-запросы.


Схема Базы Данных:
![This is an image](https://github.com/Gidrosliv/java-filmorate/blob/main/src/main/resources/schemaNew.png)

#### Выводит все поля из таблицы Film; 
``` 
SELECT film_id, name, description, release_date, duration, mpa
FROM Film
```

#### Выводит все поля у фильма с Id = ?
```
SELECT * 
FROM Film AS f LEFT JOIN likes AS l ON f.film_id = l.film_id 
WHERE f.film_id = ? 
GROUP BY f.film_id, l.id;
```

#### Выводит топ-х фильмов, где х =?
```
SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.mpa, l.id 
FROM likes AS l 
RIGHT JOIN Film AS f ON f.film_id = l.film_id 
GROUP BY f.film_id, l.id 
ORDER BY COUNT(l.id) DESC LIMIT ?
```

#### Выводит список общих друзей между пользователями с id = ?
```
SELECT f.friend_id, u.id, u.email, u.login, u.name, u.birthday 
FROM friends AS f 
LEFT JOIN friends AS fr ON f.friend_id = fr.friend_id 
INNER JOIN Person AS u ON f.friend_id = u.id 
WHERE f.id = ? AND fr.id = ? 
GROUP BY f.friend_id;
```



