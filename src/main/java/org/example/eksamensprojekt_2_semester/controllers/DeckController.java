package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.services.CollectionService;
import org.example.eksamensprojekt_2_semester.services.DeckService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;
    private final CollectionService collectionService;

    public DeckController(DeckService deckService, CollectionService collectionService) {
        this.deckService = deckService;
        this.collectionService = collectionService;
    }

    @GetMapping
    public String showDecks(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        model.addAttribute("decks", deckService.getDecksByUserId(userId));
        model.addAttribute("formatList", Format.values());
        return "/pages/decks/decks";
    }

    @PostMapping("/add")
    public String addDeck(@RequestParam String deckName, @RequestParam Format format,
                          HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        deckService.addDeck(deckName, format, userId);
        return "redirect:/decks";
    }

    @GetMapping("/{deckId}/add-cards")
    public String showAddCardsView(@PathVariable int deckId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        model.addAttribute("collectionCards", collectionService.getCards(userId));
        model.addAttribute("deckId", deckId);
        return "/pages/decks/add-card";
    }

    @PostMapping("/{deckId}/add-cards")
    public String addCardsToDeck(@PathVariable int deckId, @RequestParam(required = false) List<Integer> cardIds,
                                 HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        deckService.addCards(deckId, cardIds, userId);
        return "redirect:/decks/" + deckId;
    }

    @GetMapping("/{deckId}")
    public String showDeckInfo(@PathVariable int deckId, Model model) {
        model.addAttribute("deck", deckService.getDeckById(deckId));
        model.addAttribute("deckCards", deckService.getAllCards(deckId));
        model.addAttribute("formatList", Format.values());
        return "/pages/decks/deck-view";
    }

    @PostMapping("/{deckId}/update")
    public String updateDeck(@PathVariable int deckId, @RequestParam String deckName,
                             @RequestParam Format format, HttpSession session)  {
        Integer userId = (Integer) session.getAttribute("userId");
        deckService.updateDeck(deckId, deckName, format, userId);
        return "redirect:/decks/" + deckId;
    }
    
    @PostMapping("/{deckId}/remove-card")
    public String removeCardFromDeck(@PathVariable int deckId, @RequestParam int cardId,
                                     HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        deckService.removeCardFromDeck(userId, cardId, deckId);
        return "redirect:/decks/" + deckId;
    }

    @GetMapping("/{deckId}/delete")
    public String deleteDeck(@PathVariable int deckId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        deckService.deleteDeck(deckId, userId);
        return "redirect:/decks";
    }
}
