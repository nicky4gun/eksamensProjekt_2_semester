package org.example.eksamensprojekt_2_semester.models.interfaces;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository  {
    Optional<Card> findCardByUserId(int userId, int cardId);

    Optional<User> findUserById(int userId);


    List<Card> findCardsByUserId(int userId);




    void saveFavorites(int userId,int cardId);
    void  removeFavorites(int userId,int cardId);

    int createUser(User user);

    Optional<User> findUserByUsername(String username);

    void removeUser(int id);

    void updateUser(int id, String username, String email, String password);

    List<Card> getFavorites(int userId);
}
