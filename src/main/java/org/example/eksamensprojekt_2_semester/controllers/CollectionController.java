package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.Card;
import org.example.eksamensprojekt_2_semester.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/collection")
public class CollectionController {
    private final UserService userService;
    public CollectionController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String showCollection(Model model, HttpSession session) {
        Integer userId =(Integer) session.getAttribute("userId");
        int cardId = (Integer) session.getAttribute("cardId");
        if (userId == null) {
            {userId = 1; session.setAttribute("userId", userId); }
        }
        Optional<Card> collection = userService.findCardByUserId(userId,cardId);

        return "/pages/collection";
    }
    @GetMapping("/favorites")
    public String showFavorites(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }


        model.addAttribute("favorites", userService.getFavoriteCards(userId));
        return "/pages/collection";
    }
    @PostMapping("/favorites/add")
    public String addFavorite(@RequestParam int cardId , HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }
        // userservice.addFavoriteCard
        return"redirect:/collection/favorites";

    }
    @PostMapping("/favorites/remove")
    public String removeFavorite(@RequestParam int cardId , HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }
        // userservice.removeFavoriteCard
        return"redirect:/collection/favorites";
    }


}
