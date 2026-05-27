package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Deck;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.models.exceptions.CardNotFoundException;
import org.example.eksamensprojekt_2_semester.models.exceptions.DeckNotFoundException;
import org.example.eksamensprojekt_2_semester.models.exceptions.UserNotFoundException;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICollectionRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IDeckRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeckService {
    private final IDeckRepository deckRepository;
    private final IUserRepository userRepository;
    private final ICollectionRepository collectionRepository;

    public DeckService(IDeckRepository deckRepository, IUserRepository userRepository, ICollectionRepository collectionRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
        this.collectionRepository = collectionRepository;
    }

    public int addDeck(String deckName, Format format, int userId) {
        Deck deck = new Deck(deckName, format, userId);
        return deckRepository.createDeck(deck);
    }

    public void addCards(int deckId, List<Integer> cardIds, int userId) {
        Deck deck = validateDeckOwnership(userId, deckId);
        addCardsToDeck(deck, cardIds, userId);
    }

    private void addCardsToDeck(Deck deck, List<Integer> cardIds, int userId) {
        if (cardIds == null || cardIds.isEmpty()) return;

        List<Card> cards = fetchOwnedCards(cardIds, userId);
        linkCardsToDeck(deck, cards);
    }

    private List<Card> fetchOwnedCards(List<Integer> cardIds, int userId) {
        List<Card> cards = new ArrayList<>();

        for (int cardId : cardIds) {
            Card card = collectionRepository.findCardByUserId(userId, cardId).orElseThrow(
                    () -> new CardNotFoundException("Kort med ID " + cardId + " ikke fundet i samling!")
            );

            cards.add(card);
        }

        return cards;
    }

    private void linkCardsToDeck(Deck deck, List<Card> cards) {
        for (Card card : cards) {
            deckRepository.addCardToDeck(deck.getId(), card.getId());
            deck.addCard(card);
        }
    }

    public Deck getDeckById(int deckId) {
        return deckRepository.findDeckById(deckId).orElseThrow(
                () -> new DeckNotFoundException("Ingen deck fundet med ID " + deckId)
        );
    }

    public List<Deck> getDecksByUserId(int userId) {
        return deckRepository.findDecksByUserId(userId);
    }

    public List<Card> getAllCards(int deckId) {
        return deckRepository.findAllCards(deckId);
    }

    public void updateDeck(int deckId, String deckName, Format format, int userId) {
        Deck deck = validateDeckOwnership(userId, deckId);
        deck.setDeckName(deckName);
        deck.setFormat(format);
        deckRepository.updateDeck(deck);
    }

    public void removeCardFromDeck(int userId, int cardId, int deckId) {
        validateDeckOwnership(userId, deckId);
        deckRepository.removeCardFromDeck(cardId, deckId);
    }

    private Deck validateDeckOwnership(int userId, int deckId) {
        Deck deck = getDeckById(deckId);

        if (deck.isNotOwnedBy(userId)) {
            throw new SecurityException("Decket tilhører ikke denne bruger");
        }

        return deck;
    }

    public void deleteDeck(int deckId, int userId) {
        Deck deck = getDeckById(deckId);
        User user = userRepository.findUserById(userId).orElseThrow(
                () -> new UserNotFoundException("Ingen bruger med ID " + userId + " fundet!"));

        if (user.isNotAdmin() && deck.isNotOwnedBy(userId)) {
            throw new SecurityException("Du har ikke tilladelse til at slette dette deck!");
        }

        deckRepository.deleteDeck(deckId);
    }

    public List<Deck> getDecksByUserIdOnly5(int userId) {
        return deckRepository.getDecksByUserIdOnly5(userId);
    }
}