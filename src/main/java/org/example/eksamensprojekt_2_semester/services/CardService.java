package org.example.eksamensprojekt_2_semester.services;


import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.models.enums.Role;
import org.example.eksamensprojekt_2_semester.models.interfaces.ICardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CardService {
    private final ICardRepository cardRepository;

    public CardService(ICardRepository cardRepository) {this.cardRepository = cardRepository;}

    public Card addCard(User user, String name, CardType cardType, List<ManaColor> colors, String set, Rarity rarity, String ruleText, String imageUrl, boolean isTradable) {

     if (user.getRole() != Role.ADMIN){
         throw new SecurityException("You are not allowed to perform this action");
     }

        validateCard(name,cardType,colors,set,rarity,ruleText,imageUrl);
        Card card = new Card(name,cardType,colors,set,rarity,ruleText,imageUrl,isTradable);
        return cardRepository.createCard(card);
    }


    private void validateCard(String name,CardType cardType, List<ManaColor> colors,String set,Rarity rarity,String ruleText,String imageUrl){




    }



}
