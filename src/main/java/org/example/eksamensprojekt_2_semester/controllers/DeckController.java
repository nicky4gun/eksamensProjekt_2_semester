package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.services.DeckService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping("/add")
    public String showAddDeckForm() {
        return "add-deck";
    }

    @PostMapping("/add")
    public String addDeck(@RequestParam String deckName, @RequestParam List<Integer> cardIds,
                          @RequestParam Format format, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            deckService.addDeck(deckName, cardIds, format, userId);
        }

        return "redirect:/decks";
    }

    @PostMapping("/add-cards")
    public String addCardsToDeck(@RequestParam int deckId, @RequestParam List<Integer> cardIds, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            deckService.addCardsToExistingDeck(deckId, cardIds, userId);
        }

        return "redirect:/decks";
    }

    @GetMapping("/delete")
    public String deleteDeck(@RequestParam Integer deckId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            deckService.deleteDeck(deckId, userId);
        }
        return "redirect:/decks";
    }
}
