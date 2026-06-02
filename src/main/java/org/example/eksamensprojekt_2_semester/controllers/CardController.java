package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.enums.CardType;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.enums.Rarity;
import org.example.eksamensprojekt_2_semester.services.CardService;
import org.example.eksamensprojekt_2_semester.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;
    private final UserService userService;

    public CardController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String showCreateCardForm(Model model) {
        model.addAttribute("card", new Card());
        model.addAttribute("cardTypes", CardType.values());
        model.addAttribute("colors", ManaColor.values());
        model.addAttribute("rarities", Rarity.values());
        return "pages/admin/admin-add-card";
    }

    @PostMapping("/create")
    public String createCard(@ModelAttribute Card card, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        cardService.addCard(userId, card.getName(), card.getCardType(), card.getColor(),
                card.getExpansions(), card.getRarity(), card.getRuleText(), card.getImageUrl());

        return "redirect:/profile";
    }

    @GetMapping("/update/{cardId}")
    public String updateCardForm(@PathVariable int cardId, Model model, HttpSession session) {

        Card card = cardService.findById(cardId);
        model.addAttribute("card", card);

        model.addAttribute("cardTypes", CardType.values());
        model.addAttribute("colors", ManaColor.values());
        model.addAttribute("rarities", Rarity.values());
        return "pages/admin/admin-update-card";
    }

    @PostMapping("/update")
    public String updateCard(@ModelAttribute Card card, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        cardService.updateCard(userId, card.getId(), card.getName(), card.getCardType(),
                card.getColor(), card.getExpansions(), card.getRarity(), card.getRuleText(), card.getImageUrl());

        return "redirect:/profile/" + card.getId();
    }

    @PostMapping("/delete")
    public String deleteCard(@ModelAttribute Card card, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        cardService.deleteCard(userId, card.getId());
        return "redirect:/collection/add-cards";
    }
}
