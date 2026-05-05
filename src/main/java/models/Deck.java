package models;

import models.enums.CardType;
import models.enums.Format;

import java.util.List;

public class Deck {
    private List<Card> cards;
    private Format format;

    public Deck() {}

    public Deck(List<Card> cards, Format format) {
        if (cards == null || cards.isEmpty()) {
            throw new IllegalArgumentException("Must contain at least one card");
        }

        if (format == null) {
            throw new IllegalArgumentException("Format is required");
        }

        if(cards.size() < format.getMinCards()) {
            throw new IllegalArgumentException("Deck must have at least " + format.getMinCards() + " cards");
        }

        if(cards.size() > format.getMaxCards()) {
            throw new IllegalArgumentException("Deck must have at most " + format.getMaxCards() + "  cards ");
        }

        this.cards = cards;
        this.format = format;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }
}
