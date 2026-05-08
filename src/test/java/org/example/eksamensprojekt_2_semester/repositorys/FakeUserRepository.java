package org.example.eksamensprojekt_2_semester.repositorys;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.models.interfaces.IUserRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeUserRepository implements IUserRepository {
    private final List<Card> cards = new ArrayList<>();

    public FakeUserRepository() {
        cards.add(new Card(1,"Dingus Staff", CardType.ARTIFACT, List.of (ManaColor.COLORLESS),"Weatherlight", Rarity.COMMON,"Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller","",false));
    }

    @Override
    public List<Card> findAllCardsByUserId(int userId, List<Integer> cardIds) {
        List<Card> foundCards = new ArrayList<>();

        if (userId == 1) {
            for (Card card : cards) {
               for (int cardId : cardIds) {
                   if (card.getId() == cardId) {
                       foundCards.add(card);
                       break;
                   }
               }
            }
        }

        return foundCards;
    }
}
