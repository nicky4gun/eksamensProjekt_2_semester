package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.Role;
import org.example.eksamensprojekt_2_semester.services.CardService;
import org.example.eksamensprojekt_2_semester.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Card")
public class CardController {
    private final CardService cardService;
    private final UserService userService;

    public CardController(CardService cardService , UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @PostMapping("/Create")
    public String createCard(@ModelAttribute Card card, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }

        User user = userService.findUserById(userId).orElseThrow();

        if (user.getRole() != Role.ADMIN) {
            model.addAttribute("error", "you do not have permission to create this card");
            return "Create";
        }

        try {
            cardService.addCard(card.getId(), card.getName(), card.getCardType(), card.getColor(), card.getSet(), card.getRarity(), card.getRuleText(), card.getImageUrl());
            return "redirect:/Card/List";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "Create";
        }
    }

    @PostMapping("/Update")
    public String updateCard(@ModelAttribute Card card, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }

        User user = userService.findUserById(userId).orElseThrow();
        cardService.updateCard(user.getId(), card.getId(), card.getName(), card.getCardType(),
                card.getColor(), card.getSet(), card.getRarity(), card.getRuleText(), card.getImageUrl());
        return "redirect:/cards/"+ card.getId();
    }
}
