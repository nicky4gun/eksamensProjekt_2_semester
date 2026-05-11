package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.Role;
import org.example.eksamensprojekt_2_semester.services.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Card")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/Create")
    public String createCard(@ModelAttribute Card card, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/Login";
        }
        if (user.getRole() != Role.ADMIN) {
            model.addAttribute("error", "you do not have permission to create this card");
            return "Create";
        }

        try {
            cardService.addCard(user, card.getName(), card.getCardType(), card.getColor(), card.getSet(), card.getRarity(), card.getRuleText(), card.getImageUrl());
            return "redirect:/Card/List";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "Create";
        }
    }

    @PostMapping("/Update")
    public String updateCard(@ModelAttribute Card card, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/Login";
        }
        if (user.getRole() != Role.ADMIN) {
            model.addAttribute("error", "you do not have permission to update this card");
        }
        cardService.updateCard(
                user,
                card.getId(),
                card.getName(),
                card.getCardType(),
                card.getColor(),
                card.getSet(),
                card.getRarity(),
                card.getRuleText(),
                card.getImageUrl()


        );
        return "redirect:/Card/List";
    }
}












