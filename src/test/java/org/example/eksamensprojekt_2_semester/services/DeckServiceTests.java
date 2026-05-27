package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Deck;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.models.exceptions.CardNotFoundException;
import org.example.eksamensprojekt_2_semester.models.exceptions.DeckNotFoundException;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICollectionRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IDeckRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeckServiceTests {
    private IDeckRepository deckRepository;
    private IUserRepository userRepository;
    private ICollectionRepository collectionRepository;

    private DeckService deckService;

    private final String deckName = "Test";
    private final Format format = Format.TEST;
    private final int userId = 1;

    @BeforeEach
    void setUp() {
        deckRepository = mock(IDeckRepository.class);
        userRepository = mock(IUserRepository.class);
        collectionRepository = mock(ICollectionRepository.class);

        deckService = new DeckService(deckRepository, userRepository, collectionRepository);
    }

    @Test
    void addDeck_ShouldSave_WhenDeckIsValid() {
        deckService.addDeck(deckName, format, userId);

        ArgumentCaptor<Deck> deckCaptor = ArgumentCaptor.forClass(Deck.class);
        verify(deckRepository, times(1)).createDeck(deckCaptor.capture());

        Deck capturedDeck = deckCaptor.getValue();
        assertEquals(deckName, capturedDeck.getDeckName());
        assertEquals(format, capturedDeck.getFormat());
        assertEquals(userId, capturedDeck.getUserId());
    }

    @Test
    void addCardsToExistingDeck_ShouldAddCards() {
        int deckId = 1;
        Deck deck = new Deck(deckId, deckName, format, userId);
        List<Integer> cardIds = List.of(1, 2);
        Card card1 = mock(Card.class);
        Card card2 = mock(Card.class);

        when(card1.getId()).thenReturn(1);
        when(card2.getId()).thenReturn(2);
        when(deckRepository.findDeckById(deckId)).thenReturn(Optional.of(deck));
        when(collectionRepository.findCardByUserId(userId, 1)).thenReturn(Optional.of(card1));
        when(collectionRepository.findCardByUserId(userId, 2)).thenReturn(Optional.of(card2));

        deckService.addCards(deckId, cardIds, userId);

        assertEquals(2, deck.getCards().size());
        verify(deckRepository, times(1)).addCardToDeck(deckId, 1);
        verify(deckRepository, times(1)).addCardToDeck(deckId, 2);
    }

    @Test
    void addCardsToExistingDeck_ShouldThrowException_WhenDeckNotFound() {
        int deckId = 1;
        List<Integer> cardIds = List.of(1);

        when(deckRepository.findDeckById(deckId)).thenReturn(Optional.empty());

        assertThrows(DeckNotFoundException.class, () ->
            deckService.addCards(deckId, cardIds, userId)
        );
    }

    @Test
    void addCards_ShouldThrowException_WhenCardNotInCollection() {
        int deckId = 1;
        Deck deck = new Deck(deckId, deckName, format, userId);

        when(deckRepository.findDeckById(deckId)).thenReturn(Optional.of(deck));
        when(collectionRepository.findCardByUserId(userId, 1)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> deckService.addCards(deckId, List.of(1), userId));
    }
}
