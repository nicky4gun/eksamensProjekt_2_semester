package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Collection;
import org.example.eksamensprojekt_2_semester.models.exceptions.CardNotFoundException;
import org.example.eksamensprojekt_2_semester.models.exceptions.CollectionNotFoundException;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
    private final ICollectionRepository collectionRepository;

    public CollectionService(ICollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public void addCards(List<Integer> cardIds, int userId) {
        Collection collection = validateCollectionOwnership(userId);

        if (cardIds == null || cardIds.isEmpty()) return;

        for (Integer cardId : cardIds) {
            collectionRepository.addCard(collection.getId(), cardId);
        }
    }

    public List<Card> getCards(int userId) {
        Collection collection = validateCollectionOwnership(userId);
        return collectionRepository.findAllCards(collection.getId());
    }

    private Collection validateCollectionOwnership(int userId) {
        Collection collection = getCollectionByUserId(userId);

        if (collection.isNotOwnedBy(userId)) {
            throw new IllegalArgumentException("Samlingen tilhører ikke denne bruger");
        }

        return collection;
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
}
