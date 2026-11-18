package com.kaonykk.financetutorapi.controller;

import com.kaonykk.financetutorapi.dto.AccountResponseDTO;
import com.kaonykk.financetutorapi.dto.LimitRequestDTO;
import com.kaonykk.financetutorapi.exception.ValidationException;
import com.kaonykk.financetutorapi.service.AccountService;
import com.kaonykk.financetutorapi.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/wards")
public class ApiWardServlet extends HttpServlet {

    private final AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = requireTutor(req, resp);
        if (session == null) return;

        Long tutorId = (Long) session.getAttribute("userId");
        List<AccountResponseDTO> body = accountService.getByTutor(tutorId)
                .stream()
                .map(AccountResponseDTO::new)
                .toList();

        resp.setStatus(200);
        JsonUtil.mapper().writeValue(resp.getWriter(), body);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = requireTutor(req, resp);
        if (session == null) return;

        Long tutorId = (Long) session.getAttribute("userId");
        try {
            LimitRequestDTO body = JsonUtil.mapper().readValue(req.getReader(), LimitRequestDTO.class);
            if (body.getAccountId() == null || body.getDailyLimit() == null) {
                throw new ValidationException("Escribe un límite válido");
            }
            accountService.updateLimit(tutorId, body.getAccountId(), body.getDailyLimit());
            resp.setStatus(200);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("message", "Límite actualizado"));
        } catch (ValidationException e) {
            resp.setStatus(400);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", e.getMessage()));
        }
    }

    private HttpSession requireTutor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.setStatus(401);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", "Debes iniciar sesión"));
            return null;
        }
        if (!"TUTOR".equals(session.getAttribute("userRole"))) {
            resp.setStatus(403);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", "Solo un tutor puede gestionar límites"));
            return null;
        }
        return session;
    }
}