DROP TABLE IF EXISTS FilmGenre CASCADE;
DROP TABLE IF EXISTS Genre CASCADE;
DROP TABLE IF EXISTS Person CASCADE;
DROP TABLE IF EXISTS Film CASCADE;
DROP TABLE IF EXISTS Rate CASCADE;
DROP TABLE IF EXISTS Likes CASCADE;
DROP TABLE IF EXISTS Status CASCADE;
DROP TABLE IF EXISTS Friends CASCADE;

INSERT INTO Genre(genre_name)
VALUES ('COMEDY'),
       ('DRAMA'),
       ('CARTOON'),
       ('THRILLER'),
       ('DOCUMENTARY'),
       ('ACTION');


INSERT INTO Rate(rate)
VALUES ('G'),
       ('PG'),
       ('PG-13'),
       ('R'),
       ('NC-17');

INSERT INTO Person(email, login, name, birthday)
VALUES('mail', 'login', 'name', '1990-03-08');

INSERT INTO Film(name, description, release_date, duration, mpa)
VALUES ('The Rock', 'Starring Nicolas Cage and Sean Connery', '1996-06-07', '136', '1');