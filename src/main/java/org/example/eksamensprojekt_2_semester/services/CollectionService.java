package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Collection;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.exceptions.CardNotFoundException;
import org.example.eksamensprojekt_2_semester.models.exceptions.CollectionNotFoundException;
import org.example.eksamensprojekt_2_semester.models.exceptions.UserNotFoundException;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICollectionRepository;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;
import org.example.eksamensprojekt_2_semester.repositorys.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
    private final ICollectionRepository collectionRepository;
    private final IUserRepository userRepository;
    private final CardRepository cardRepository;

    public CollectionService(ICollectionRepository collectionRepository, IUserRepository userRepository, CardRepository cardRepository) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public void addCards(List<Integer> cardIds, int userId) {
        Collection collection = getCollectionByUserId(userId);

        if (collection.getUserId() != userId) {
            throw new IllegalArgumentException("Samlingen tilhører ikke denne bruger");
        }

        for (Integer cardId : cardIds) {
            collectionRepository.addCard(collection.getId(), cardId);
        }
    }

    public List<Card> getCards(int userId) {
        Collection collection = getCollectionByUserId(userId);

        if (collection.getUserId() != userId) {
            throw new IllegalArgumentException("Samlingen tilhører ikke denne bruger");
        }

        return collectionRepository.findAllCards(collection.getId());
    }

    public Collection getCollectionByUserId(int userId) {
        return collectionRepository.findByUserId(userId).orElseThrow(
                () -> new CollectionNotFoundException("Samling for bruger med ID " + userId + " ikke fundet!")
        );
    }

    public Card findCardByUserId(int userId, int cardId) {
        return collectionRepository.findCardByUserId(userId, cardId).orElseThrow(
                () -> new CardNotFoundException("Kort med ID " + cardId + " ikke fundet i brugerens samling!")
        );
    }

    public List<Card> findCardsByUserId(int userId) {
        return collectionRepository.findCardsByUserId(userId);
    }

    public List<Card> findTheFirst10Cards(int collectionId ) {
       return collectionRepository.findTheFirst10Cards(collectionId);
    }

    public void addFavoriteCard(int userId, int cardId) {
        collectionRepository.saveFavorites(userId, cardId);
    }

    public void removeFavoriteCard(int userId, int cardId) {
        User user = userRepository.findUserById(userId).orElseThrow(
                () -> new UserNotFoundException("Bruger med ID "+ userId + " ikke fundet!"));

        cardRepository.findCardById(cardId);
        collectionRepository.removeFavorites(userId, cardId);
    }

    public List<Card>  getFavoriteCardsLimitBy10(Integer userId) {
        return collectionRepository.getFavoritesLimitBy10(userId);
    }

    public List<Card> getFavoriteCards(int userId) {
        return collectionRepository.getFavorites(userId);
    }
}
