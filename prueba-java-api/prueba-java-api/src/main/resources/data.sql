DELETE FROM article_author;
DELETE FROM article;
DELETE FROM author;
DELETE FROM category;

ALTER TABLE article_author AUTO_INCREMENT = 1;
ALTER TABLE article AUTO_INCREMENT = 1;
ALTER TABLE author AUTO_INCREMENT = 1;
ALTER TABLE category AUTO_INCREMENT = 1;


INSERT INTO category (name, status) VALUES
('Tecnología', 'ACTIVE'),
('Vida', 'ACTIVE'),
('Opinión', 'INACTIVE');

INSERT INTO author (name, email, status) VALUES
('Leonardo Jurado', 'ljurado@gmail.com', 'ACTIVE'),
('Enrique Pineda', 'epineda@gmail.com', 'ACTIVE'),
('Arturo Carbajal', 'acarbajal@hotmail.com', 'INACTIVE');
