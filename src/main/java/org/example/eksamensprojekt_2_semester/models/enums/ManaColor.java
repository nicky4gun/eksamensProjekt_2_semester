package org.example.eksamensprojekt_2_semester.models.enums;

public enum ManaColor {
    RED("Red"),
    BLUE("Blue"),
    GREEN("Green"),
    WHITE("White"),
    BLACK("Black"),
    COLORLESS("Colorless");

    private final String color;

    ManaColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
