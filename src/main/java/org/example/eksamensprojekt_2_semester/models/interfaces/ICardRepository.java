package org.example.eksamensprojekt_2_semester.models.interfaces;

import org.example.eksamensprojekt_2_semester.models.Card;


public interface ICardRepository {
    int createCard(Card card);

    void updateCard(Card card);

    Card findCardById(int cardId);
}
