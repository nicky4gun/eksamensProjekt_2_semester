package org.example.eksamensprojekt_2_semester.models.enums;

public enum Rarity {
    COMMON("Common"),
    UNCOMMON("Uncommon"),
    RARE("Rare"),
    MYTHIC_RARE("Mythic_rare");


    private String rarity;

    Rarity(String rarity) {
        this.rarity = rarity;
    }
}
