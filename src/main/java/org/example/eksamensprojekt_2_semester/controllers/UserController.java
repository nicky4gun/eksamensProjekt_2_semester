package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/signup";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.registerUser(user.getFirstName(),user.getLastName(),user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(), user.getImage() );
        return "redirect:/login";
    }
    @PostMapping("/login")
    public String login(HttpSession session, Model model, @ModelAttribute User user) {
        User loggedIn = userService.loginUser(user.getEmail(), user.getPassword());

        if (loggedIn != null) {
            session.setAttribute("loggedInMember", loggedIn);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "auth/login";
        }
    }
}
