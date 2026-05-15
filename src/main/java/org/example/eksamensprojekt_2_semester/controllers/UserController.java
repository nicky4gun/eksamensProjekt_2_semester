package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showprofile(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userid");
        if (userId == null) {userId = 1; session.setAttribute("userId", userId); }
        User user = userService.findUserById(userId).orElseThrow();
        model.addAttribute("user", user);
        return "/pages/profile";
    }
}
