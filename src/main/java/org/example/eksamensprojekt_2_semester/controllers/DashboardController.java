package org.example.eksamensprojekt_2_semester.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class DashboardController {

    @GetMapping
    public String showDashboard() {
        return "pages/dashboard";
    }
}
