package org.example.eksamensprojekt_2_semester.models.interfaces;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Collection;
import org.example.eksamensprojekt_2_semester.models.enums.Visibility;

import java.util.List;
import java.util.Optional;

public interface ICollectionRepository {
    void createCollection(int userId, Visibility visibility);

    void addCard(int collectionId, Integer cardId);

    Optional<Collection> findById(int collectionId);

    Optional<Collection> findByUserId(int userId);

    List<Card> findAllCards(int collectionId);
}
