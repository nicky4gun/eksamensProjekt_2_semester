CREATE DATABASE MTGDB;

CREATE TABLE IF NOT EXISTS users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    first_Name  VARCHAR(50),
    last_Name   VARCHAR(50),
    username    VARCHAR(50)  not null UNIQUE,
    password    VARCHAR(255) not null,
    email       VARCHAR(100) not null UNIQUE,
    role        VARCHAR(50)  not null
);

CREATE TABLE IF NOT EXISTS cards (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150) not null,
    card_type   VARCHAR(50)  not null,
    color       VARCHAR(50)  not null,
    expansions  VARCHAR(150) not null,
    rarity      VARCHAR(40)  not null,
    rule_text   VARCHAR(500),
    image_url   VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS decks (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    deck_Name   VARCHAR(50) not null,
    format      VARCHAR(50) not null,
    user_id     INT         not null,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS deck_cards (
    deck_id INT,
    card_id INT,

    PRIMARY KEY (deck_id, card_id),
    FOREIGN KEY (deck_id) REFERENCES decks(id) ON DELETE CASCADE,
    FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS collections (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT UNIQUE,
    visibility  VARCHAR(40),

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS collection_cards (
    collection_id   INT,
    card_id         INT,

    PRIMARY KEY (collection_id, card_id),
    FOREIGN KEY (collection_id) REFERENCES collections(id) ON DELETE CASCADE,
    FOREIGN KEY (card_id)       REFERENCES cards(id) ON DELETE CASCADE
);