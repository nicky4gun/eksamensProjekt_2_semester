package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Deck;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.models.interfaces.IDeckRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public class DeckService {
    private final IDeckRepository deckRepository;
    private final IUserRepository userRepository;

    public DeckService(IDeckRepository deckRepository, IUserRepository userRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
    }

    public void addDeck(String deckName, List<Integer> cardIds, Format format, int userId) {
        Deck deck = new Deck(deckName, format, userId);

        if (cardIds != null && !cardIds.isEmpty()) {
            List<Card> cards = userRepository.findAllCardsByUserId(userId, cardIds);

            for (Card card : cards) {
                deck.addCard(card);
            }
        }

        deckRepository.createDeck(deck);
    }

    public List<Deck> getDecksByUserId(int userId) {
        return deckRepository.findDecksByUserId(userId);
    }
}
