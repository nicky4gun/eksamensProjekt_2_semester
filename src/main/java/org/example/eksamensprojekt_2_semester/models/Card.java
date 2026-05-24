package org.example.eksamensprojekt_2_semester.models;

import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;

public class Card {
    private int id;
    private String name;
    private CardType cardType;
    private ManaColor color;
    private String expansions;
    private Rarity rarity;
    private String ruleText;
    private String imageUrl;
    private boolean isTradable;

    public Card() {}

    public Card(String name, CardType cardType, ManaColor color, String expansions, Rarity rarity, String ruleText, String imageUrl) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (cardType == null) {
            throw new IllegalArgumentException("Invalid card type");
        }

        if (color == null) {
            throw new IllegalArgumentException("Colors cannot be null or empty");
        }

        if (expansions == null || expansions.isEmpty()) {
            throw new IllegalArgumentException("Set cannot be null or empty");
        }

        if (rarity == null) {
            throw new IllegalArgumentException("Invalid rarity");
        }

        this.name = name;
        this.cardType = cardType;
        this.color = color;
        this.expansions = expansions;
        this.rarity = rarity;
        this.ruleText = ruleText;
        this.imageUrl = imageUrl;
    }

    public Card(int id, String name, CardType cardType, ManaColor color, String expansions, Rarity rarity, String ruleText, String imageUrl) {
        if (id < 0) {
            throw new IllegalArgumentException("Invalid card id");

        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (cardType == null) {
            throw new IllegalArgumentException("Invalid card type");
        }

        if (color == null) {
            throw new IllegalArgumentException("Colors cannot be null or empty");
        }

        if (expansions == null || expansions.isEmpty()) {
            throw new IllegalArgumentException("Set cannot be null or empty");
        }

        if (rarity == null) {
            throw new IllegalArgumentException("Invalid rarity");
        }

        if (ruleText == null || ruleText.isEmpty()) {
            throw new IllegalArgumentException("Rule text cannot be null or empty");
        }

        this.id = id;
        this.name = name;
        this.cardType = cardType;
        this.color = color;
        this.expansions = expansions;
        this.rarity = rarity;
        this.ruleText = ruleText;
        this.imageUrl = imageUrl;
    }

    public Card(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public ManaColor getColor() {
        return color;
    }

    public void setColor(ManaColor color) {
        this.color = color;
    }

    public String getExpansions() {
        return expansions;
    }

    public void setExpansions(String expansions) {
        this.expansions = expansions;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getRuleText() {
        return ruleText;
    }

    public void setRuleText(String ruleText) {
        this.ruleText = ruleText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isTradable() {
        return isTradable;
    }

    public void setIsTradable(boolean isTradable) {
        this.isTradable = isTradable;
    }
}