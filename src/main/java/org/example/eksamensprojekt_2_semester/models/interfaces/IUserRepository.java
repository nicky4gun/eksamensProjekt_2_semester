package org.example.eksamensprojekt_2_semester.models.interfaces;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;

import java.util.List;

public interface IUserRepository {
    List<Card> findCardsByUserId(int userId, List<Integer> cardIds);

    User findUserById(int userId);

}
