package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.enums.ManaColor;
import org.example.eksamensprojekt_2_semester.services.CardService;
import org.example.eksamensprojekt_2_semester.services.CollectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection")
public class CollectionController {
    private final CardService cardService;
    private final CollectionService collectionService;

    public CollectionController(CardService cardService, CollectionService collectionService) {
        this.cardService = cardService;
        this.collectionService = collectionService;
    }

    @GetMapping
    public String showCollection(@RequestParam(required = false) String search, @RequestParam(required = false) List<ManaColor> colors,
                                 Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        List<Card> cards = collectionService.findCardsByUserId(userId);
        cards = cardService.searchCards(cards, search);
        cards = cardService.filterCardsByColor(cards, colors);
        model.addAttribute("collection", cards);
        model.addAttribute("manaColor", ManaColor.values());
        model.addAttribute("collectionId", userId);

        return "/pages/collections/collection";
    }

    @GetMapping("/add-cards")
    public String showAddCards(Model model) {
        model.addAttribute("cardsAvailable", cardService.findAll());
        return "/pages/collections/add-to-collection";
    }

    @PostMapping("/add-cards")
    public String addCard(@RequestParam(required = false) List<Integer> cardIds, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        collectionService.addCards(cardIds, userId);
        return "redirect:/collection";
    }
}
