package com.kaonykk.financetutorapi.controller;

import com.kaonykk.financetutorapi.dto.MovementRequestDTO;
import com.kaonykk.financetutorapi.exception.InsufficientBalanceException;
import com.kaonykk.financetutorapi.exception.LimitExceededException;
import com.kaonykk.financetutorapi.exception.ValidationException;
import com.kaonykk.financetutorapi.model.enums.WithdrawalResult;
import com.kaonykk.financetutorapi.service.TransactionService;
import com.kaonykk.financetutorapi.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/movements")
public class ApiMovementServlet extends HttpServlet {

    private final TransactionService transactionService = new TransactionService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.setStatus(401);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", "Debes iniciar sesión"));
            return;
        }
        Long userId = (Long) session.getAttribute("userId");

        try {
            MovementRequestDTO body = JsonUtil.mapper().readValue(req.getReader(), MovementRequestDTO.class);
            if (body.getAmount() == null) {
                throw new ValidationException("Escribe un monto válido");
            }
            String description = body.getDescription() == null ? "" : body.getDescription();
            String message;

            if ("INCOME".equals(body.getType())) {
                transactionService.deposit(userId, body.getAmount(), description);
                message = "Ingreso registrado";
            } else if ("WITHDRAWAL".equals(body.getType())) {
                WithdrawalResult result = transactionService.withdraw(userId, body.getAmount(), description);
                message = (result == WithdrawalResult.COMPLETED)
                        ? "Retiro realizado"
                        : "Supera tu límite diario: se envió a tu tutor para aprobación";
            } else {
                throw new ValidationException("Tipo de movimiento inválido");
            }

            resp.setStatus(200);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("message", message));

        } catch (ValidationException | InsufficientBalanceException | LimitExceededException e) {
            resp.setStatus(400);
            JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", e.getMessage()));
        }
    }
}