package org.example.eksamensprojekt_2_semester.models;

import org.example.eksamensprojekt_2_semester.models.enums.Visibility;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private int id;
    private int userId;
    private final List<Card> cards = new ArrayList<>();
    private Visibility visibility;

    public Collection() {}

    public Collection(int id, int userId, Visibility visibility) {
        if (userId < 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        if (visibility == null) {
            throw new IllegalArgumentException("Invalid visibility");
        }

        this.id = id;
        this.userId = userId;
        this.visibility = visibility;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isNotOwnedBy(int userId) {
        return getUserId() != userId;
    }
}
