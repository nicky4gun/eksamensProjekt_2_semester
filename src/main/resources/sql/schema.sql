CREATE DATABASE MTGDB

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_Name VARCHAR(50),
    last_Name VARCHAR(50),
    user_Name VARCHAR(50) not null,
    pasword VARCHAR(255) not null,
    email VARCHAR(100) not null
    role VARCHAR(50) not null,
);

CREATE TABLE IF NOT EXISTS cards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) not null,
    card_Type VARCHAR(50) not null,
    colors VARCHAR(50) not null,
    set VARCHAR(150) not null,
    rarity VARCHAR(40) not null,
    rule_text VARCHAR(500),
    imageUrl VARCHAR(150),
    isTradeble BOOLEAN not null
);

CREATE TABLE IF NOT EXISTS decks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    deck_Name VARCHAR(50) not null,
    format VARCHAR(50) not null,
    user_id INT not null,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS deck_cards (
    deck_id INT PRIMARY KEY,
    card_id INT PRIMARY KEY,

    FOREIGN KEY (deck_id) REFERENCES decks(id),
    FOREIGN KEY (card_id) REFERENCES cards(id)
);

CREATE TABLE IF NOT EXISTS collections (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    visibility VARCHAR(40),

    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS collection_cards (
    collection_id INT PRIMARY KEY,
    card_id INT PRIMARY KEY,

    FOREIGN KEY (collection_id) REFERENCES collections(id),
    FOREIGN KEY (card_id) REFERENCES cards(id)
);




