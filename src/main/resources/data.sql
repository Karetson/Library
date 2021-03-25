INSERT INTO users (created_at, email, first_name, last_name, password, role) VALUES
    (null,  'orzeleagle122@gmail.com', 'Patryk', 'Orzelowski', 'zaq1@WSX', 2),
    (null, 'email@email.com', 'Anindd', 'Allenaar', 'zaq1@WSX', 2),
    (null, 'email1@email.com', 'Itrfar', 'Manalon', 'zaq1@WSX', 1),
    (null, 'email2@email.com', 'Enarid', 'Haunfan', 'zaq1@WSX', 0),
    (null, 'email3@email.com', 'Garemir', 'Thussth', 'zaq1@WSX', 0);
INSERT INTO genres (genre) VALUES ('Comedy');
INSERT INTO genres (genre) VALUES ('Fantasy');
INSERT INTO genres (genre) VALUES ('Adventure');
INSERT INTO genres (genre) VALUES ('Biographical');
INSERT INTO genres (genre) VALUES ('Criminal');
INSERT INTO genres (genre) VALUES ('Natural');
INSERT INTO genres (genre) VALUES ('Historical');
INSERT INTO genres (genre) VALUES ('Detective');
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul1', 'Autor1', 'Wydawnictwo1', 15, 14);
INSERT INTO books_genres (books_id, genres_id) VALUES (1, 2);
INSERT INTO books_genres (books_id, genres_id) VALUES (1, 4);
INSERT INTO books_genres (books_id, genres_id) VALUES (1, 1);
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul2', 'Autor1', 'Wydawnictwo2', 11, 6);
INSERT INTO books_genres (books_id, genres_id) VALUES (2, 6);
INSERT INTO books_genres (books_id, genres_id) VALUES (2, 4);
INSERT INTO books_genres (books_id, genres_id) VALUES (2, 2);
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul6', 'Autor1', 'Wydawnictwo1', 15, 15);
INSERT INTO books_genres (books_id, genres_id) VALUES (3, 6);
INSERT INTO books_genres (books_id, genres_id) VALUES (3, 3);
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul1', 'Autor2', 'Wydawnictwo1', 10, 4);
INSERT INTO books_genres (books_id, genres_id) VALUES (4, 8);
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul4', 'Autor1', 'Wydawnictwooooo', 12, 9);
INSERT INTO books_genres (books_id, genres_id) VALUES (5, 8);
INSERT INTO books_genres (books_id, genres_id) VALUES (5, 1);
INSERT INTO books_genres (books_id, genres_id) VALUES (5, 2);
INSERT INTO books_genres (books_id, genres_id) VALUES (5, 5);
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul1', 'Autor3', 'Chyba', 5, 1);
INSERT INTO books_genres (books_id, genres_id) VALUES (6, 3);
INSERT INTO books_genres (books_id, genres_id) VALUES (6, 4);
INSERT INTO books_genres (books_id, genres_id) VALUES (6, 2);
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul23', 'Autor2', 'Nie', 17, 17);
INSERT INTO books_genres (books_id, genres_id) VALUES (7, 7);
INSERT INTO books_genres (books_id, genres_id) VALUES (7, 6);
INSERT INTO books_genres (books_id, genres_id) VALUES (7, 2);
INSERT INTO books_genres (books_id, genres_id) VALUES (7, 5);
INSERT INTO books_genres (books_id, genres_id) VALUES (7, 1);
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul11', 'Autor121', 'Niespodzianka', 3, 0);
INSERT INTO books_genres (books_id, genres_id) VALUES (8, 2);
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul123', 'Autor122', 'Wydawnictwosdfvasd', 2, 2);
INSERT INTO books_genres (books_id, genres_id) VALUES (9, 2);
INSERT INTO books_genres (books_id, genres_id) VALUES (9, 4);
INSERT INTO books_genres (books_id, genres_id) VALUES (9, 1);
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul16', 'Autor121', 'Wydawnictrqwe', 11, 2);
INSERT INTO books_genres (books_id, genres_id) VALUES (10, 2);
INSERT INTO books_genres (books_id, genres_id) VALUES (10, 8);
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul1432', 'Autor122', 'Nie', 7, 2);
INSERT INTO books_genres (books_id, genres_id) VALUES (11, 1);
INSERT INTO books_genres (books_id, genres_id) VALUES (11, 6);
INSERT INTO books (title, author, publisher, count, available) VALUES ('Tytul1', 'Autor1', 'Wydawnictwo1', 6, 4);
INSERT INTO books_genres (books_id, genres_id) VALUES (12, 7);
INSERT INTO books_genres (books_id, genres_id) VALUES (12, 3);