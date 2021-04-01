-- initializing users
INSERT INTO users (created_at, email, first_name, last_name, password, role) VALUES
    (null,  'orzeleagle122@gmail.com', 'Patryk', 'Orzelowski', 'zaq1@WSX', 2),
    (null, 'email@email.com', 'Anindd', 'Allenaar', 'zaq1@WSX', 2),
    (null, 'email1@email.com', 'Itrfar', 'Manalon', 'zaq1@WSX', 1),
    (null, 'email2@email.com', 'Enarid', 'Haunfan', 'zaq1@WSX', 0),
    (null, 'email3@email.com', 'Garemir', 'Thussth', 'zaq1@WSX', 0);

-- initializing books
INSERT INTO books (title, author, publisher, count, available) VALUES
    ('Tytul1', 'Autor1', 'Wydawnictwo1', 15, 14),
    ('Tytul2', 'Autor1', 'Wydawnictwo2', 11, 6),
    ('Tytul6', 'Autor1', 'Wydawnictwo1', 15, 15),
    ('Tytul1', 'Autor2', 'Wydawnictwo1', 10, 4),
    ('Tytul4', 'Autor1', 'Wydawnictwooooo', 12, 9),
    ('Tytul1', 'Autor3', 'Chyba', 5, 1),
    ('Tytul23', 'Autor2', 'Nie', 17, 17),
    ('Tytul11', 'Autor121', 'Niespodzianka', 3, 0),
    ('Tytul123', 'Autor122', 'Wydawnictwosdfvasd', 2, 2),
    ('Tytul16', 'Autor121', 'Wydawnictrqwe', 11, 2),
    ('Tytul1432', 'Autor122', 'Nie', 7, 2),
    ('Tytul1', 'Autor1', 'Wydawnictwo1', 6, 4);

-- initializing genres
INSERT INTO genres (name) VALUES
    ('Comedy'), ('Fantasy'), ('Adventure'), ('Biographical'), ('Criminal'), ('Natural'), ('Historical'), ('Detective');

-- initializing genres for books
INSERT INTO books_genres (books_id, genres_id) VALUES
    (1, 2), (1, 4), (1, 1),
    (2, 6), (2, 4), (2, 2),
    (3, 6), (3, 3),
    (4, 8),
    (5, 8), (5, 1), (5, 2), (5, 5),
    (6, 3), (6, 4), (6, 2),
    (7, 7), (7, 6), (7, 2), (7, 5), (7, 1),
    (8, 2),
    (9, 2), (9, 4), (9, 1),
    (10, 2), (10, 8),
    (11, 1), (11, 6),
    (12, 7), (12, 3);
