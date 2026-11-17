package com.kaonykk.financetutorapi.controller;

import com.kaonykk.financetutorapi.dto.DecisionRequestDTO;
import com.kaonykk.financetutorapi.dto.RequestResponseDTO;
import com.kaonykk.financetutorapi.exception.InsufficientBalanceException;
import com.kaonykk.financetutorapi.exception.ValidationException;
import com.kaonykk.financetutorapi.model.entity.AuthorizationRequest;
import com.kaonykk.financetutorapi.service.AuthorizationService;
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

@WebServlet("/api/requests")
public class ApiRequestServlet extends HttpServlet {

	private final TransactionService transactionService = new TransactionService();
	private final AuthorizationService authorizationService = new AuthorizationService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = requireSession(req, resp);
		if (session == null) {
			return;
		}

		Long userId = (Long) session.getAttribute("userId");
		String role = (String) session.getAttribute("userRole");

		List<AuthorizationRequest> list = "TUTOR".equals(role)
			? authorizationService.getPending(userId)
			: transactionService.getRequests(userId);

		List<RequestResponseDTO> body = list.stream().map(RequestResponseDTO::new).toList();
		resp.setStatus(200);
		JsonUtil.mapper().writeValue(resp.getWriter(), body);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = requireSession(req, resp);
		if (session == null) {
			return;
		}

		Long userId = (Long) session.getAttribute("userId");
		String role = (String) session.getAttribute("userRole");

		if (!"TUTOR".equals(role)) {
			resp.setStatus(403);
			JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", "Solo un tutor puede decidir solicitudes"));
			return;
		}

		try {
			DecisionRequestDTO body = JsonUtil.mapper().readValue(req.getReader(), DecisionRequestDTO.class);
			String message;
			if ("approve".equals(body.getAction())) {
				authorizationService.approve(userId, body.getRequestId());
				message = "Solicitud aprobada";
			} else if ("reject".equals(body.getAction())) {
				authorizationService.reject(userId, body.getRequestId());
				message = "Solicitud rechazada";
			} else {
				throw new ValidationException("Acción inválida");
			}
			resp.setStatus(200);
			JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("message", message));
		} catch (ValidationException | InsufficientBalanceException e) {
			resp.setStatus(400);
			JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", e.getMessage()));
		}
	}

	private HttpSession requireSession(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			resp.setStatus(401);
			JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", "Debes iniciar sesión"));
			return null;
		}
		return session;
	}
}
