package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.models.Collection;
import org.example.eksamensprojekt_2_semester.models.enums.Format;
import org.example.eksamensprojekt_2_semester.services.CardService;
import org.example.eksamensprojekt_2_semester.services.CollectionService;
import org.example.eksamensprojekt_2_semester.services.DeckService;
import org.example.eksamensprojekt_2_semester.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/home")
public class DashboardController {
    private final UserService userService;
    private final CardService cardService;
    private final CollectionService collectionService;
    private final DeckService deckService;

    public DashboardController(UserService userService, CardService cardService, CollectionService collectionService, DeckService deckService) {
        this.userService = userService;
        this.cardService = cardService;
        this.collectionService = collectionService;
        this.deckService = deckService;
    }

    @GetMapping
    public String showDashboard(Model model, HttpSession session) {
       Integer userId = (Integer) session.getAttribute("userId");
        Collection collection = collectionService.getCollectionByUserId(userId);

            model.addAttribute("collection", collectionService.findTheFirst10Cards(collection.getId()));
        model.addAttribute("favorites", userService.getFavoriteCardsLimitBy10(userId));
        model.addAttribute("decks", deckService.getDecksByUserIdOnly5(userId));
        model.addAttribute("formatList", Format.values());
            model.addAttribute("collectionId", collection.getId());

        return "pages/dashboard";


    }






}