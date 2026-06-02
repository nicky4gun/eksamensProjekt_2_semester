package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.services.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/create")
    public String createCard(@ModelAttribute Card card, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        cardService.addCard(userId, card.getName(), card.getCardType(), card.getColor(),
                card.getExpansions(), card.getRarity(), card.getRuleText(), card.getImageUrl());

        return "redirect:/users/profile";
    }

    @PostMapping("/update")
    public String updateCard(@ModelAttribute Card card, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        cardService.updateCard(userId, card.getId(), card.getName(), card.getCardType(),
                card.getColor(), card.getExpansions(), card.getRarity(), card.getRuleText(), card.getImageUrl());

        return "redirect:/users/profile/"+ card.getId();
    }
}
