package org.example.eksamensprojekt_2_semester.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {
        String path = request.getRequestURI();

        if (path.startsWith("/login") || path.startsWith("/register")) {
            return true;
        }

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}