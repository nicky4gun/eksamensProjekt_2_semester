package org.example.eksamensprojekt_2_semester.models;

import org.example.eksamensprojekt_2_semester.models.enums.Format;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String deckName;
    private Format format;
    private List<Card> cards = new ArrayList<Card>();
    private int userId;

    public Deck() {}

    public Deck(String deckName, Format format, int userId) {
        if (deckName == null || deckName.isEmpty()) {
            throw new IllegalArgumentException("The deck must have a name");
        }

        if (format == null) {
            throw new IllegalArgumentException("Format is required");
        }

        this.deckName = deckName;
        this.format = format;
        this.userId = userId;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null");
        }

        cards.add(card);
    }

    public boolean isDeckValid() {
        if (cards == null || cards.isEmpty()) {
            return false;
        }

        return cards.size() >= format.getMinCards() && cards.size() <= format.getMaxCards();
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
