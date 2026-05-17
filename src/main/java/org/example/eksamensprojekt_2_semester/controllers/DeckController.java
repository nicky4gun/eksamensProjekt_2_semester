package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Deck;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.services.DeckService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping
    public String showDecks(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null)
            {userId = 1; session.setAttribute("userId", userId); }


        model.addAttribute("decks", deckService.getDecksByUserId(userId));
        model.addAttribute("formatList", Format.values());
        return "/pages/decks";
    }

    @PostMapping("/add")
    public String addDeck(@RequestParam String deckName, @RequestParam Format format, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }

        int deckId = deckService.addDeck(deckName, format, userId);
        return "redirect:/decks";
    }

    @PostMapping("/add-cards")
    public String addCardsToDeck(@RequestParam int deckId, @RequestParam List<Integer> cardIds, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }

        deckService.addCardsToDeck(deckId, cardIds, userId);
        return "redirect:/decks";
    }

    @GetMapping("/{deckId}")
    public String showDeckInfo(@PathVariable int deckId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }

        Deck deck = deckService.getDeckById(deckId);
        List<Card> deckCards = deckService.getAllCards(deckId);
        model.addAttribute("deck", deck);
        model.addAttribute("deckCards", deckCards);
        model.addAttribute("formatList", Format.values());
        return "/pages/deck-view";
    }

    @PostMapping("/{deckId}/update")
    public String updateDeck(@PathVariable int deckId, @RequestParam String deckName,
                             @RequestParam Format format, HttpSession session)  {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }

        deckService.updateDeck(deckId, deckName, format, userId);
        return "redirect:/decks/" + deckId;
    }
    
    @PostMapping("/{deckId}/remove-card")
    public String removeCardFromDeck(@PathVariable int deckId, @RequestParam int cardId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }

        deckService.removeCardFromDeck(userId, cardId, deckId);
        return "redirect:/decks/" + deckId;
    }

    @GetMapping("/{deckId}/delete")
    public String deleteDeck(@PathVariable int deckId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }

        deckService.deleteDeck(deckId, userId);
        return "redirect:/decks";
    }
}
