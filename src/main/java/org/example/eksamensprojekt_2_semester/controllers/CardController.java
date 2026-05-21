package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.models.enums.Role;
import org.example.eksamensprojekt_2_semester.models.exceptions.UserNotFoundException;
import org.example.eksamensprojekt_2_semester.services.CardService;
import org.example.eksamensprojekt_2_semester.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;
    private final UserService userService;

    public CardController(CardService cardService , UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createCard(@ModelAttribute Card card, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }

        try {
            User user = userService.findUserById(userId);

            if (user.getRole() != Role.ADMIN) {
                model.addAttribute("error", "Du har ikke tilladelse til at oprette dette kort!");
                return "create";
            }

            cardService.addCard(card.getId(), card.getName(), card.getCardType(), card.getColor(), card.getExpansions(), card.getRarity(), card.getRuleText(), card.getImageUrl());
            return "redirect:/Card/List";
        } catch (IllegalArgumentException | UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "create";
        }
    }

    @PostMapping("/update")
    public String updateCard(@ModelAttribute Card card, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }

        try {
            User user = userService.findUserById(userId);

            cardService.updateCard(user.getId(), card.getId(), card.getName(), card.getCardType(),
                    card.getColor(), card.getExpansions(), card.getRarity(), card.getRuleText(), card.getImageUrl());

            return "redirect:/cards/"+ card.getId();

        } catch (IllegalArgumentException | UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "update";
        }
    }
}
