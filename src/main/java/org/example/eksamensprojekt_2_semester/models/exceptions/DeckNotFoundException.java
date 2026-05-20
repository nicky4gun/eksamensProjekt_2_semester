package org.example.eksamensprojekt_2_semester.models.exceptions;

public class DeckNotFoundException extends RuntimeException {
    public DeckNotFoundException(String message) {
        super(message);
    }
}
