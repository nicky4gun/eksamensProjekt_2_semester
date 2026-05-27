package org.example.eksamensprojekt_2_semester.models;

import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTests {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void cardList_ShouldStartEmpty() {
        assertTrue(deck.getCards().isEmpty());
    }

    @Test
    void ValidConstructor_ShouldInitializeDeck() {
        Format format = Format.STANDARD;
        Deck newDeck = new Deck("My Deck", format, 1);

        assertEquals("My Deck", newDeck.getDeckName());
        assertEquals(format, newDeck.getFormat());
        assertEquals(1, newDeck.getUserId());
        assertTrue(newDeck.getCards().isEmpty());
    }

    @Test
    void addCard_ShouldAddCard_WhenCardIsProvided() {
        deck.addCard(new Card());
        assertEquals(1, deck.getCards().size());
    }

    @Test
    void addCard_ShouldThrowException_WhenCardIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Deck().addCard(null));
    }
}
