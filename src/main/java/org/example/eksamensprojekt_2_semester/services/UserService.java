package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICardRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final ICardRepository cardRepository;

    public UserService(IUserRepository userRepository, ICardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public Optional<Card> findCardByUserId(int userId, int cardId) {
        return userRepository.findCardByUserId(userId, cardId);
    }

    public Optional<User> findUserById(int userId) {
        return userRepository.findUserById(userId);
    }

    public List<Card> getFavoriteCards(int userId) {
       User user = userRepository.findUserById(userId).orElseThrow();
        return user.getFavoriteCards();
    }
    public List<Card> findCardsByUserId(int userId) {
        return userRepository.findCardsByUserId(userId);

    }
}
