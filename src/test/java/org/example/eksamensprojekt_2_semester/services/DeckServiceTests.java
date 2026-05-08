package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Deck;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.repositorys.FakeDeckRepository;
import org.example.eksamensprojekt_2_semester.repositorys.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckServiceTests {

    private DeckService deckService;

    @BeforeEach
    void setUp() {
        deckService = new DeckService(new FakeDeckRepository(), new FakeUserRepository());
    }

    @Test
    void createDeck_ShouldSave_WhenDeckIsValid() {
        String deckName = "Agro";
        List<Integer> cardIds = List.of(1);
        Format format = Format.CASUAL;
        int userId = 1;

        deckService.addDeck(deckName, cardIds, format, userId);
        
        List<Deck> savedDecks = deckService.getAllDecksByUserId(userId);
        assertEquals(1, savedDecks.size());
        assertEquals(deckName, savedDecks.getFirst().getDeckName());
    }
}
