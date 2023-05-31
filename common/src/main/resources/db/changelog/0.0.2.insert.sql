INSERT INTO roles (name)
VALUES ('Admin'),
       ('User'),
       ('Guest');

INSERT INTO users (first_name, last_name, login, password, role_id)
VALUES ('John', 'Doe', 'johndoe', 'password', 1),
       ('Jane', 'Smith', 'janesmith', 'password', 2),
       ('Mike', 'Johnson', 'mikejohnson', 'password', 2);

INSERT INTO genres (name)
VALUES ('Science Fiction'),
       ('Fantasy'),
       ('Mystery'),
       ('Romance'),
       ('Thriller');

INSERT INTO books (title, cost, page_count, author_id)
VALUES ('Book 1', 10.99, 200, 1),
       ('Book 2', 12.99, 250, 2),
       ('Book 3', 9.99, 180, 3);

INSERT INTO books_genres (book_id, genre_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 4),
       (3, 3),
       (3, 5);
