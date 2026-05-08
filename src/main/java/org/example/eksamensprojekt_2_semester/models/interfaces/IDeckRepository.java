package org.example.eksamensprojekt_2_semester.models.interfaces;

import org.example.eksamensprojekt_2_semester.models.Deck;

import java.util.List;

public interface IDeckRepository {
    void createDeck(Deck deck);

    List<Deck> findDecksByUserId(int userId);
}
