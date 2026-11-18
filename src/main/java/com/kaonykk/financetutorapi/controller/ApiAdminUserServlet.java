package com.kaonykk.financetutorapi.controller;

import com.kaonykk.financetutorapi.dto.UserRegistrationDTO;
import com.kaonykk.financetutorapi.dto.UserResponseDTO;
import com.kaonykk.financetutorapi.exception.DuplicateEmailException;
import com.kaonykk.financetutorapi.exception.ValidationException;
import com.kaonykk.financetutorapi.model.entity.User;
import com.kaonykk.financetutorapi.service.AssignmentService;
import com.kaonykk.financetutorapi.service.UserService;
import com.kaonykk.financetutorapi.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/admin/users")
public class ApiAdminUserServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final AssignmentService assignmentService = new AssignmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = requireAdmin(req, resp);
        if (session == null) return;

        String role = req.getParameter("role");
        List<User> users;
        if ("TUTOR".equals(role)) {
            users = assignmentService.getTutors();
        } else if ("PRINCIPAL".equals(role)) {
            users = assignmentService.getPrincipals();
        } else {
            resp.setStatus(400);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", "Indica role=TUTOR o role=PRINCIPAL"));
            return;
        }

        List<UserResponseDTO> body = users.stream().map(UserResponseDTO::new).toList();
        resp.setStatus(200);
        JsonUtil.mapper().writeValue(resp.getWriter(), body);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = requireAdmin(req, resp);
        if (session == null) return;

        try {
            UserRegistrationDTO body = JsonUtil.mapper().readValue(req.getReader(), UserRegistrationDTO.class);
            userService.register(body);
            resp.setStatus(201);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("message", "Usuario registrado"));
        } catch (ValidationException | DuplicateEmailException e) {
            resp.setStatus(400);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", e.getMessage()));
        }
    }

    private HttpSession requireAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.setStatus(401);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", "Debes iniciar sesión"));
            return null;
        }
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            resp.setStatus(403);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", "Solo un administrador puede gestionar usuarios"));
            return null;
        }
        return session;
    }
}