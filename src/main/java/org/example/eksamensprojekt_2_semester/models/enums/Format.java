package org.example.eksamensprojekt_2_semester.models.enums;

public enum Format {
    COMMANDER(100,100),
    STANDARD(60,1000),
    CASUAL(60,1000),
    BOOSTER_DRAFT(40,100),
    MODERN(60,60),
    BRAWL(60,60),
    TIMELESS(60,250),
    TEST(1, 100);

    private final int minCards;
    private final int maxCards;

    Format(int minCards, int maxCards) {
        this.minCards = minCards;
        this.maxCards = maxCards;
    }

    public int getMinCards() {
        return minCards;
    }

    public int getMaxCards() {
        return maxCards;
    }
}

