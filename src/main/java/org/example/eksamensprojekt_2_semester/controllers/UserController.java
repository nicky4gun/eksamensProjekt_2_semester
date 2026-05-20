package org.example.eksamensprojekt_2_semester.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojekt_2_semester.models.User;
import org.example.eksamensprojekt_2_semester.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "/pages/users/profile";
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
        User loggedIn = userService.loginUser(user.getUsername(), user.getPassword());

        if (loggedIn != null) {
            session.setAttribute("userId", loggedIn.getId());
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "auth/login";
        }
    }

    @GetMapping("/profile/update")
    public String ShowUpdateProfile(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return "pages/users/update";
    }

    @PostMapping("/profile/update")
    public String updateProfile(HttpSession session, Model model, @ModelAttribute User user, @RequestParam String password, @RequestParam String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "/pages/users/update";
        }

        userService.updateUser(user.getId(), user.getUsername(), user.getEmail(), password);
        session.setAttribute("userId", user.getId());
        return "redirect:/profile";
    }

    @PostMapping("/members/delete/{id}")
    public String deleteUser(HttpSession session, @PathVariable int id) {
        Integer userId = (Integer) session.getAttribute("userId");
        userService.removeUser(id);

        if (userId != null && userId == id) {
            session.invalidate();
            return "redirect:/login";
        }

        return "redirect:/profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
