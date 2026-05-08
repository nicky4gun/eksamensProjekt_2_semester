package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Collection;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.models.enums.Role;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICardRepository;
import org.example.eksamensprojekt_2_semester.repositorys.FakeCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.example.eksamensprojekt_2_semester.models.enums.ManaColor.COLORLESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardServicesTest {
    private  FakeCardRepository fakeCardRepository ;
    private  CardServices cardService;
    @BeforeEach
    void setUp() {
        fakeCardRepository = new FakeCardRepository();
        cardService = new CardServices(fakeCardRepository);
    }

    @Test
    void addCard_ShouldAddNewCard() {
        User user = new User("bob","han","bygger","1234","bobhan@bygger.com", Role.ADMIN, new Collection());
        int expectedId = 1;

        Card result = cardService.addCard(user,"Dingus Staff", CardType.ARTIFACT, List.of (ManaColor.COLORLESS),"Weatherlight", Rarity.COMMON,"Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller","",false);
        assertEquals(expectedId, result.getId());
    }

    @Test
    void addCard_ShouldNotAddNewCardWhenNoName() {
        User user = new User("bob","han","bygger","1234","bobhan@bygger.com", Role.ADMIN, new Collection());
        assertThrows(IllegalArgumentException.class,()-> cardService.addCard(user ,"",CardType.ARTIFACT, List.of (ManaColor.COLORLESS),"Weatherlight", Rarity.COMMON,"Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller","",false));
    }

}
