package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Deck;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.models.enums.Role;
import org.example.eksamensprojekt_2_semester.models.interfaces.IDeckRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {
    private final IDeckRepository deckRepository;
    private final IUserRepository userRepository;

    public DeckService(IDeckRepository deckRepository, IUserRepository userRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
    }

    public void deleteDeck(int deckId, int userId ) {
        Deck deck = deckRepository.findDeckById(deckId).orElseThrow();
        User user = userRepository.findUserById(userId);
        if (user.getRole() != Role.ADMIN && deck.getUserId() != userId){
            throw new SecurityException("You cannot delete this deck");
        }

        deckRepository.deleteDeck(deckId);
    }

    public void addDeck(String deckName, List<Integer> cardIds, Format format, int userId) {
        Deck deck = new Deck(deckName, format, userId);
        deckRepository.createDeck(deck);
        addCardsToDeck(deck, cardIds, userId);
    }

    public void addCardsToExistingDeck(int deckId, List<Integer> cardIds, int userId) {
        Deck deck = deckRepository.findDeckById(deckId).orElseThrow();

        if (deck.getUserId() != userId) {
            throw new SecurityException("Decket tilhører ikke denne bruger");
        }

        addCardsToDeck(deck, cardIds, userId);
    }

    private void addCardsToDeck(Deck deck, List<Integer> cardIds, int userId) {
        if (cardIds != null && !cardIds.isEmpty()) {
            List<Card> cards = userRepository.findCardsByUserId(userId, cardIds);

            for (Card card : cards) {
                deckRepository.addCardToDeck(deck.getId(), card.getId());
                deck.addCard(card);
            }
        }
    }

    public List<Deck> getDecksByUserId(int userId) {
        return deckRepository.findDecksByUserId(userId);
    }

    public void removeCardFromDeck(int userId, int cardId, int deckId) {
        Deck deck = deckRepository.findDeckById(deckId).orElseThrow();

        if (deck.getUserId() != userId) {
            throw new SecurityException("Decket tilhører ikke denne bruger");
        }

        deckRepository.removeCardFromDeck(cardId, deckId);
    }

}