package com.kaonykk.financetutorapi.controller;

import com.kaonykk.financetutorapi.dto.AssignmentRequestDTO;
import com.kaonykk.financetutorapi.exception.ValidationException;
import com.kaonykk.financetutorapi.service.AssignmentService;
import com.kaonykk.financetutorapi.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/admin/assignments")
public class ApiAssignmentServlet extends HttpServlet {

	private final AssignmentService assignmentService = new AssignmentService();

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
		if (!"ADMIN".equals(session.getAttribute("userRole"))) {
			resp.setStatus(403);
			JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", "Solo un administrador puede vincular tutores"));
			return;
		}

		try {
			AssignmentRequestDTO body = JsonUtil.mapper().readValue(req.getReader(), AssignmentRequestDTO.class);
			assignmentService.assign(body.getTutorId(), body.getPrincipalId());
			resp.setStatus(201);
			JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("message", "Tutor vinculado correctamente"));
		} catch (ValidationException e) {
			resp.setStatus(400);
			JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(500);
			JsonUtil.mapper().writeValue(resp.getWriter(),
				Map.of("error", "Error interno: " + e.getClass().getSimpleName()));
		}
	}
}
