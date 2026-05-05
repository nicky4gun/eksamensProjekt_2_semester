package models.enums;

public enum Format {
    COMMANDER(100,100),
    STANDARD(1000,60),
    CASUAL(1000,60),
    BOOSTER_DRAFT(100,40),
    MODERN(60,60),
    BRAWL(60,60),
    TIMELESS(250,60);


    private final int maxCards;
    private final int minCards;

    Format(int maxCards, int minCards) {
        this.maxCards = maxCards;
        this.minCards = minCards;
    }

    public int getMaxCards() {
        return maxCards;
    }
    public int getMinCards() {
        return minCards;
    }
}

