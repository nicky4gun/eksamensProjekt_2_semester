package org.example.eksamensprojekt_2_semester.services;

import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Collection;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.models.enums.Role;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICardRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardServiceTests {
    private  ICardRepository cardRepository;
    private CardService cardService;

    @BeforeEach
    void setUp() {
        cardRepository = mock(ICardRepository.class);
        cardService = new CardService(cardRepository);
    }

    @Test
    void addCard_ShouldAddNewCard() {
        User user = new User("bob","han","bygger","1234","bobhan@bygger.com", Role.ADMIN, new Collection());

        Card card = new Card("Dingus Staff", CardType.ARTIFACT, List.of(ManaColor.COLORLESS),"Weatherlight", Rarity.COMMON,
                "Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller",
                "");
        when(cardRepository.createCard(any(Card.class))).thenReturn(card.getId());

        cardService.addCard(user,"Dingus Staff", CardType.ARTIFACT, List.of (ManaColor.COLORLESS),"Weatherlight", Rarity.COMMON,
                "Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller",
                "");
        verify(cardRepository, times(1)).createCard(any(Card.class));
    }

    @Test
    void addCard_ShouldNotAddNewCardWhenNoName() {
        User user = new User("bob","han","bygger","1234","bobhan@bygger.com", Role.ADMIN, new Collection());
        assertThrows(IllegalArgumentException.class,()->
                cardService.addCard(user ,"",CardType.ARTIFACT, List.of (ManaColor.COLORLESS),"Weatherlight", Rarity.COMMON,
                        "Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller",
                        ""));

        verify(cardRepository, never()).createCard(any(Card.class));
    }
    @Test
    void addCard_ShouldNotAddNewCardWhenNoColor() {
        User user = new User("bob","han","bygger","1234","bobhan@bygger.com", Role.ADMIN, new Collection());
        assertThrows(IllegalArgumentException.class,()->
                cardService.addCard(user,"Dingus Staff",CardType.ARTIFACT,List.of(),"Weatherlight", Rarity.COMMON,"Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller",
                        ""));

    }
    @Test
    void addCard_ShouldNotAddNewCardWhenNoRarity() {
        User user = new User("bob","han","bygger","1234","bobhan@bygger.com", Role.ADMIN, new Collection());
        assertThrows(IllegalArgumentException.class,()->
                cardService.addCard(user,"Dingus Staff",CardType.ARTIFACT,List.of(ManaColor.COLORLESS),"Weatherlight", null,"Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller",
                        ""));

    }
    @Test
    void addCard_ShouldNotAddNewCardWhenNoSet() {
        User user = new User("bob","han","bygger","1234","bobhan@bygger.com", Role.ADMIN, new Collection());
        assertThrows(IllegalArgumentException.class,()->
                cardService.addCard(user,"Dingus Staff",CardType.ARTIFACT,List.of(ManaColor.COLORLESS),"", Rarity.COMMON,"Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller",
                        ""));
    }
    @Test
    void addCard_ShouldNotAddNewCardWhenNoCardType() {
        User user = new User("bob","han","bygger","1234","bobhan@bygger.com", Role.ADMIN, new Collection());
        assertThrows(IllegalArgumentException.class,()->
                cardService.addCard(user,"Dingus Staff",null,List.of(ManaColor.COLORLESS),"Weatherlight", Rarity.COMMON,"Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller",
                        ""));

    }
    @Test
    void User_CantBeAUserHasToBeAAdmin(){
        User user = new User("bob","han","bygger","1234","bobhan@bygger.com", Role.USER, new Collection());

      assertThrows(SecurityException.class, ()-> cardService.addCard(user ,"Dingus Staff",CardType.ARTIFACT, List.of (ManaColor.COLORLESS),"Weatherlight", Rarity.COMMON,
                "Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller",
                ""));
    }
    @Test
    void updateCard_ShouldnotUpdateCardWHenIdIsNegative() {
        User user = new User("bob","han","bygger","1234","bobhan@bygger.com", Role.ADMIN, new Collection());
        assertThrows(IllegalArgumentException.class,()->
                cardService.updateCard(user,-1,"Dingus Staff",null,List.of(ManaColor.COLORLESS),"Weatherlight", Rarity.COMMON,"Whenever a creature is put into any graveyard from play, Dingus Staff deals 2 damage to that creatures controller",
                        ""));


    }


}
