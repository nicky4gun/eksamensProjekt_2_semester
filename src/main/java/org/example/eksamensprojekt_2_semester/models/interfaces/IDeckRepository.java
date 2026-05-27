package org.example.eksamensprojekt_2_semester.models.interfaces;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Deck;

import java.util.List;
import java.util.Optional;

public interface IDeckRepository {
    int createDeck(Deck deck);

    void addCardToDeck(int deckId, int cardId);

    List<Deck> findDecksByUserId(int userId);

    Optional<Deck> findDeckById(int deckId);

    List<Card> findAllCards(int deckId);

    void updateDeck(Deck deck);

    void removeCardFromDeck(int cardId, int deckId);

    void deleteDeck(int deckId);

    List<Deck> getDecksByUserIdOnly5(int userId);

}
