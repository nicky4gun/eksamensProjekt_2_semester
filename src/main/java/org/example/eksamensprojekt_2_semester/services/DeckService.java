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
        List<Card> cards = userRepository.findAllCardsByUserId(userId, cardIds);
        Deck deck = new Deck(deckName, format, userId);

        for (Card card : cards) {
            deck.addCard(card);
        }

        if (!deck.isDeckValid()) {
            throw new IllegalStateException("Deck is not valid for format: " + format.name());
        }

        deckRepository.createDeck(deck);
    }

    public List<Deck> getAllDecksByUserId(int userId) {
        return deckRepository.findDecksByUserId(userId);
    }
}
