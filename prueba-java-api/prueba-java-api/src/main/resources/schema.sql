CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS author (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS article (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    slug VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL UNIQUE,
    body TEXT,
    status VARCHAR(20) NOT NULL,
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_article_category FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS article_author (
    article_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (article_id, author_id),
    FOREIGN KEY (article_id) REFERENCES article(id),
    FOREIGN KEY (author_id) REFERENCES author(id)
);
