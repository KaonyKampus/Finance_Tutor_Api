package com.kaonykk.financetutorapi.controller;

import com.kaonykk.financetutorapi.dto.TransactionResponseDTO;
import com.kaonykk.financetutorapi.service.TransactionService;
import com.kaonykk.financetutorapi.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/transactions")
public class ApiTransactionServlet extends HttpServlet {

    private final TransactionService transactionService = new TransactionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.setStatus(401);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", "Debes iniciar sesión"));
            return;
        }

        Long userId = (Long) session.getAttribute("userId");

        List<TransactionResponseDTO> body = transactionService.getHistory(userId)
                .stream()
                .map(TransactionResponseDTO::new)
                .toList();

        resp.setStatus(200);
        JsonUtil.mapper().writeValue(resp.getWriter(), body);
    }
}