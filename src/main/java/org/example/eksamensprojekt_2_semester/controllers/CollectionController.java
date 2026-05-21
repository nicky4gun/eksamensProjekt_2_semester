package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.models.exceptions.CollectionNotFoundException;
import org.example.eksamensprojekt_2_semester.models.exceptions.UserNotFoundException;
import org.example.eksamensprojekt_2_semester.services.CardService;
import org.example.eksamensprojekt_2_semester.services.CollectionService;
import org.example.eksamensprojekt_2_semester.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection")
public class CollectionController {
    private final UserService userService;
    private final CardService cardService;
    private final CollectionService collectionService;

    public CollectionController(UserService userService, CardService cardService, CollectionService collectionService) {
        this.userService = userService;
        this.cardService = cardService;
        this.collectionService = collectionService;
    }

    @GetMapping
    public String showCollection(@RequestParam(required = false) String search, @RequestParam(required = false) List<ManaColor> colors,
                                 Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            userId = 1;
        }

        try {
            List<Card> cards = userService.findCardsByUserId(userId);
            cards = userService.searchCards(cards, search);
            cards = userService.filterCardsByColor(cards, colors);
            model.addAttribute("collection", cards);
            model.addAttribute("manaColor", ManaColor.values());
            model.addAttribute("collectionId", userId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/pages/collection";
        }

        return "/pages/collection";
    }

    @GetMapping("/{collectionId}/add-cards")
    public String showAddCards(@PathVariable int collectionId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            userId = 1;
            session.setAttribute("userId", userId);
        }

        try {
            model.addAttribute("cardsAvailable", cardService.findAll());
            return "/pages/add-to-collection";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/pages/collection";
        }
    }

    @PostMapping("/{collectionId}/add-cards")
    public String addCard(@PathVariable int collectionId, @RequestParam List<Integer> cardIds, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            userId = 1;
            session.setAttribute("userId", userId);
        }

        try {
            collectionService.addCards(cardIds, collectionId, userId);
            return "redirect:/collection";
        } catch (IllegalArgumentException | CollectionNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "/pages/collection";
        }
    }

    @GetMapping("/favorites")
    public String showFavorites(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            userId = 1;
            session.setAttribute("userId", userId);
        }

        try {
            model.addAttribute("favorites", userService.getFavoriteCards(userId));
            return "/pages/collection";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/pages/collection";
        }
    }

    @PostMapping("/favorites/add")
    public String addFavorite(@RequestParam int cardId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            userId = 1;
            session.setAttribute("userId", userId);
        }

        try {
            userService.addFavoriteCard(userId,cardId);
            return "redirect:/collection/favorites";
        } catch (IllegalArgumentException e) {
            session.setAttribute("error", e.getMessage());
            return "redirect:/collection/favorites";
        }
    }

    @PostMapping("/favorites/remove")
    public String removeFavorite(@RequestParam int cardId, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            userId = 1;
            session.setAttribute("userId", userId);
        }

        try {
            userService.removeFavoriteCard(userId,cardId);
            return "redirect:/collection/favorites";
        } catch (IllegalArgumentException | UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "/pages/collection";
        }
    }
}
