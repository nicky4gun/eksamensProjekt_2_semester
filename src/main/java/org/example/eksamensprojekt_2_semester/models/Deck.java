package org.example.eksamensprojekt_2_semester.models;

import org.example.eksamensprojekt_2_semester.models.enums.Format;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private int id;
    private String deckName;
    private Format format;
    private final List<Card> cards = new ArrayList<>();
    private int userId;
    private int cardCount;

    public Deck() {}

    public Deck(String deckName, Format format, int userId) {
        if (deckName == null || deckName.isEmpty()) {
            throw new IllegalArgumentException("Angiv venligst navn på Decket!");
        }

        if (format == null) {
            throw new IllegalArgumentException("Vælg venligst et format!");
        }

        this.deckName = deckName;
        this.format = format;
        this.userId = userId;
    }

    public Deck(int id, String deckName, Format format, int userId) {
        if (deckName == null || deckName.isEmpty()) {
            throw new IllegalArgumentException("Angiv venligst navn på Decket!");
        }

        if (format == null) {
            throw new IllegalArgumentException("Vælg venligst et format!");
        }

        this.id = id;
        this.deckName = deckName;
        this.format = format;
        this.userId = userId;
    }

    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Vælg venligst et kort at tilføje!");
        }

        cards.add(card);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        if (deckName == null || deckName.isEmpty()) {
            throw new IllegalArgumentException("Angiv venligst navn på Decket!");
        }

        this.deckName = deckName;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        if (format == null) {
            throw new IllegalArgumentException("Vælg venligst et format!");
        }

        this.format = format;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCardCount() { return cardCount; }

    public void setCardCount(int cardCount) { this.cardCount = cardCount; }

    public boolean isNotOwnedBy(int userId) {
        return getUserId() != userId;
    }
}
