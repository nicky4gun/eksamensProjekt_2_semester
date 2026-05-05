package models.enums;

public enum ManaColor {
    RED("Red"),
    BLUE("Blue"),
    GREEN("Green"),
    WHITE("White"),
    BLACK("Black");

    private final String color;

    ManaColor(String color) {
        this.color = color;
    }
}
