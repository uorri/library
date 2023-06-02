CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    login      VARCHAR NOT NULL UNIQUE ,
    password   VARCHAR NOT NULL,
    role_id    INT NOT NULL,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE genres
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE books
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR NOT NULL,
    cost       FLOAT   NOT NULL,
    page_count INT     NOT NULL,
    author_id  INT  NOT NULL,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE books_genres
(
    book_id  INT NOT NULL,
    genre_id INT NOT NULL,
    PRIMARY KEY (book_id, genre_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (id),
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genres (id)
);








