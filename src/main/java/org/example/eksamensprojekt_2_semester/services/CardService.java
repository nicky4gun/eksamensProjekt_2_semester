package org.example.eksamensprojekt_2_semester.services;


import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.models.enums.Role;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICardRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.example.eksamensprojekt_2_semester.repositorys.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private final ICardRepository cardRepository;
    private final IUserRepository userRepository;

    public CardService(ICardRepository cardRepository, IUserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }


    public int addCard(int userId, String name, CardType cardType, ManaColor colors, String set, Rarity rarity, String ruleText, String imageUrl) {
        User user = userRepository.findUserById(userId).orElseThrow();

        if (user.getRole() != Role.ADMIN) {
            throw new SecurityException("You are not allowed to perform this action");
        }


        Card card = new Card(name, cardType, colors, set, rarity, ruleText, imageUrl);
        return cardRepository.createCard(card);
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public void updateCard(int userId, int id, String name, CardType cardType, ManaColor colors, String set, Rarity rarity, String ruleText, String imageUrl) {
        User user = userRepository.findUserById(userId).orElseThrow();
        if (user.getRole() != Role.ADMIN) {
            throw new SecurityException("You are not allowed to perform this action");
        }


        Card card = new Card(id, name, cardType, colors, set, rarity, ruleText, imageUrl);
        cardRepository.updateCard(card);

    }
    public void findCardById(int  cardId) {
        cardRepository.findCardById(cardId);

    }

}




