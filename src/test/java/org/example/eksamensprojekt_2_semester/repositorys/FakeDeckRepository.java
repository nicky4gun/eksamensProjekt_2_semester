package org.example.eksamensprojekt_2_semester.repositorys;

import org.example.eksamensprojekt_2_semester.models.Deck;
import org.example.eksamensprojekt_2_semester.models.interfaces.IDeckRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeDeckRepository implements IDeckRepository {
    private List<Deck> decks = new ArrayList<>();
    private int nextInt = 1;

    @Override
    public void createDeck(Deck deck) {
        decks.add(deck);
        nextInt++;
    }

    @Override
    public List<Deck> findDecksByUserId(int userId) {
        List<Deck> userDecks = new ArrayList<>();

        for (Deck deck : decks) {
            if (deck.getUserId() == userId) {
                userDecks.add(deck);
            }
        }

        return userDecks;
    }
}
