package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.repositorys.CollectionRepository;
import org.example.eksamensprojekt_2_semester.services.CardService;
import org.example.eksamensprojekt_2_semester.services.CollectionService;
import org.example.eksamensprojekt_2_semester.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        List<Card> cards = userService.findCardsByUserId(userId);
        cards = userService.searchCards(cards, search);
        cards = userService.filterCardsByColor(cards, colors);
        model.addAttribute("collection", cards);
        model.addAttribute("manaColor", ManaColor.values());
        model.addAttribute("collectionId", userId);
        return "/pages/collection";
    }

    @GetMapping("/{collectionId}/add-cards")
    public String showAddCards(@PathVariable int collectionId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            userId = 1;
            session.setAttribute("userId", userId);
        }

        model.addAttribute("cardsAvailable", cardService.findAll());
        return "/pages/add-to-collection";
    }

    @PostMapping("/{collectionId}/add-cards")
    public String addCard(@PathVariable int collectionId, @RequestParam List<Integer> cardIds, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            userId = 1;
            session.setAttribute("userId", userId);
        }

        collectionService.addCards(cardIds, collectionId, userId);
        return "redirect:/collection";
    }

    @GetMapping("/favorites")
    public String showFavorites(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            userId = 1;
            session.setAttribute("userId", userId);
        }


        model.addAttribute("favorites", userService.getFavoriteCards(userId));
        return "/pages/collection";
    }

    @PostMapping("/favorites/add")
    public String addFavorite(@RequestParam int cardId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            userId = 1;
            session.setAttribute("userId", userId);
        }
        userService.addFavoriteCard(userId,cardId);
        return "redirect:/collection/favorites";

    }

    @PostMapping("/favorites/remove")
    public String removeFavorite(@RequestParam int cardId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            userId = 1;
            session.setAttribute("userId", userId);
        }
        userService.removeFavoriteCard(userId,cardId);
        return "redirect:/collection/favorites";
    }


}
