package org.example.eksamensprojekt_2_semester.models.interfaces;

import org.example.eksamensprojekt_2_semester.models.Deck;

import java.util.List;
import java.util.Optional;

public interface IDeckRepository {
    void createDeck(Deck deck);

    List<Deck> findDecksByUserId(int userId);

    void addCardToDeck(int deckId, int cardId);

    void removeCardFromDeck(int cardId, int deckId);

    void deleteDeck(int deckId);

    Optional<Deck> findDeckById(int deckId);
}
