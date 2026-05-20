package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Collection;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
    private final ICollectionRepository collectionRepository;

    public CollectionService(ICollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public void addCards(List<Integer> cardIds, int collectionId, int UserId) {
        Collection collection = getCollectionByUserId(UserId);

        if (collection.getUserId() != UserId) {
            throw new IllegalArgumentException("Collection does not belong to the user");
        }

        for (Integer cardId : cardIds) {
            collectionRepository.addCard(collectionId, cardId);
        }
    }

    public List<Card> getCards(int userId) {
        Collection collection = getCollectionByUserId(userId);

        if (collection.getUserId() != userId) {
            throw new IllegalArgumentException("Collection does not belong to the user");
        }

        return collectionRepository.findAllCards(collection.getUserId());
    }

    public Collection getCollectionByUserId(int userId) {
        return collectionRepository.findByUserId(userId).orElseThrow();
    }
}
