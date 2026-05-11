package org.example.eksamensprojekt_2_semester.models.interfaces;

import org.example.eksamensprojekt_2_semester.models.Card;

import java.util.List;

public interface IUserRepository {
    List<Card> findCardsByUserId(int userId, List<Integer> cardIds);
}
