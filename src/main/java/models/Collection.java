package models;

import models.enums.Visibility;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private int userId;
    private final List<Card> cards = new ArrayList<>();
    private Visibility visibility;

    public Collection() {}

    public Collection(int userId, Visibility visibility) {
        if (userId < 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        if (visibility == null) {
            throw new IllegalArgumentException("Invalid visibility");
        }

        this.userId = userId;
        this.visibility = visibility;
    }

    public int  getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
