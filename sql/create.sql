
-- ---

USE library;

-- ---

DROP TABLE IF EXISTS reservation_book;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS author;

CREATE TABLE IF NOT EXISTS author (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    last_name   VARCHAR(255),
    first_name  VARCHAR(255),
    birth_date  DATE
);

CREATE TABLE IF NOT EXISTS genre (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS book (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(255),
    description TEXT,
    pages_count INTEGER,
    state       VARCHAR(255),
    author_id   INTEGER,
    genre_id    INTEGER,

    CONSTRAINT FK_book_author FOREIGN KEY (author_id) REFERENCES author(id),
    CONSTRAINT FK_book_genre FOREIGN KEY (genre_id) REFERENCES genre(id)
);

CREATE TABLE IF NOT EXISTS client (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    last_name   VARCHAR(255),
    first_name  VARCHAR(255),
    email       VARCHAR(255),
    age         INTEGER
);

CREATE TABLE IF NOT EXISTS notice (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    book_id     INTEGER,
    rating      INTEGER
);

CREATE TABLE IF NOT EXISTS reservation (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    client_id   INTEGER
--    date
);

ALTER TABLE reservation
ADD CONSTRAINT FK_reservation_client FOREIGN KEY (client_id) REFERENCES client(id);

CREATE TABLE IF NOT EXISTS reservation_book (
    reservation_id  INTEGER,
    book_id         INTEGER
);

ALTER TABLE reservation_book
ADD CONSTRAINT FK_reservation_book_reservation FOREIGN KEY (reservation_id) REFERENCES reservation(id);

ALTER TABLE reservation_book
ADD CONSTRAINT FK_reservation_book_book FOREIGN KEY (book_id) REFERENCES book(id);

INSERT INTO author(last_name, first_name, birth_date) VALUES
('DUPONDT', 'Martin', '1980-10-20'),
('LAPÊCHE', 'Ella', '1981-12-23');

INSERT INTO genre(name) VALUES
('fantastique'),
('policier');

INSERT INTO book(title, description, pages_count, state, author_id) VALUES
('Madame Bovary', '', 12, 'NEUF', 1),
('Le médecin malgré lui', '', 12, 'BON ETAT', 1),
('L''étranger', '', 12, 'ABIME', 2),
('Voyage au bout de la nuit', '', 12, 'NEUF', 2),
('Théâtre', '', 12, 'BON ETAT', 1),
('Bartleby', '', 10, 'ABIME', 2);
