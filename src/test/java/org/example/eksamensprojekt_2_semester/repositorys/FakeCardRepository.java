package org.example.eksamensprojekt_2_semester.repositorys;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICardRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeCardRepository implements ICardRepository {
    private List<Card> cards = new ArrayList<>();
    private int nextId = 1;

    @Override
     public Card createCard(Card card) {
        card.setId(nextId++);
        cards.add(card);
        return card;
    }

}
