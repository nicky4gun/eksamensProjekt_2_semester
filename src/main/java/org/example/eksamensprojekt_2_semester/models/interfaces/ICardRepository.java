package org.example.eksamensprojekt_2_semester.models.interfaces;

import org.example.eksamensprojekt_2_semester.models.Card;

import java.util.List;
import java.util.Optional;


public interface ICardRepository {
    int createCard(Card card);

    List<Card> findAll();

    void updateCard(Card card);

    Optional<Card> findCardById(int cardId);


}
