package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Deck;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.models.interfaces.IDeckRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeckServiceTests {
    private IDeckRepository deckRepository;
    private IUserRepository userRepository;

    private DeckService deckService;

    private final String deckName = "Test";
    private final Format format = Format.TEST;
    private final int userId = 1;

    @BeforeEach
    void setUp() {
        deckRepository = mock(IDeckRepository.class);
        userRepository = mock(IUserRepository.class);

        deckService = new DeckService(deckRepository, userRepository);
    }

    @Test
    void addDeck_ShouldSave_WhenDeckIsValid_WithCards() {
        List<Integer> cardIds = List.of(1);
        Card card = new Card(1 ,"Dingus Staff", CardType.ARTIFACT, ManaColor.COLORLESS, "Weatherlight", Rarity.COMMON,
                "Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller",
                "");
        when(userRepository.findCardsByUserId(userId, cardIds)).thenReturn(List.of(card));

        Deck expectedDeck = new Deck(deckName, format, userId);
        when(deckRepository.findDecksByUserId(userId)).thenReturn(List.of(expectedDeck));

        deckService.addDeck(deckName, cardIds, format, userId);
        verify(deckRepository, times(1)).createDeck(any(Deck.class));

        List<Deck> savedDecks = deckService.getDecksByUserId(userId);
        assertEquals(1, savedDecks.size());
        assertEquals(deckName, savedDecks.getFirst().getDeckName());
    }

    @Test
    void addDeck_ShouldSave_WhenDeckIsValid_WithoutCards() {
        List<Integer> cardIds = List.of();
        Deck expectedDeck = new Deck(deckName, format, userId);
        when(deckRepository.findDecksByUserId(userId)).thenReturn(List.of(expectedDeck));

        deckService.addDeck(deckName, cardIds, format, userId);

        verify(deckRepository, times(1)).createDeck(any(Deck.class));

        List<Deck> results = deckService.getDecksByUserId(userId);
        assertEquals(1, results.size());
        assertEquals(deckName, results.getFirst().getDeckName());
        assertTrue(results.getFirst().getCards().isEmpty());
    }

    @Test
    void addDeck_ShouldThrowException_WhenDeckNameIsEmpty() {
        List<Integer> cardIds = List.of();

        assertThrows(IllegalArgumentException.class, () ->
            deckService.addDeck("", cardIds, format, userId)
        );

        verify(deckRepository, never()).createDeck(any(Deck.class));
    }

    @Test
    void addDeck_ShouldThrowException_WhenFormatIsNull() {
        List<Integer> cardIds = List.of();

        assertThrows(IllegalArgumentException.class, () ->
            deckService.addDeck(deckName, cardIds, null, userId)
        );

        verify(deckRepository, never()).createDeck(any(Deck.class));
    }

    @Test
    void addCardsToExistingDeck_ShouldAddCards() {
        int deckId = 1;
        Deck deck = new Deck(deckId, deckName, format, userId);
        List<Integer> cardIds = List.of(1, 2);
        Card card1 = new Card(1, "Card 1", CardType.CREATURE, ManaColor.WHITE, "Set", Rarity.COMMON, "Text", "URL");
        Card card2 = new Card(2, "Card 2", CardType.SORCERY, ManaColor.BLUE, "Set", Rarity.RARE, "Text", "URL");

        when(deckRepository.findDeckById(deckId)).thenReturn(Optional.of(deck));
        when(userRepository.findCardsByUserId(userId, cardIds)).thenReturn(List.of(card1, card2));

        deckService.addCardsToExistingDeck(deckId, cardIds, userId);

        assertEquals(2, deck.getCards().size());
        assertTrue(deck.getCards().contains(card1));
        assertTrue(deck.getCards().contains(card2));
        verify(deckRepository, times(1)).addCardToDeck(deckId, 1);
        verify(deckRepository, times(1)).addCardToDeck(deckId, 2);
    }

    @Test
    void addCardsToExistingDeck_ShouldThrowException_WhenDeckNotFound() {
        int deckId = 1;
        List<Integer> cardIds = List.of(1);

        when(deckRepository.findDeckById(deckId)).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class, () ->
            deckService.addCardsToExistingDeck(deckId, cardIds, userId)
        );
    }
}
