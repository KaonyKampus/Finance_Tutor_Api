package com.kaonykk.financetutorapi.controller;

import com.kaonykk.financetutorapi.dto.LoginRequestDTO;
import com.kaonykk.financetutorapi.dto.LoginResponseDTO;
import com.kaonykk.financetutorapi.exception.InvalidCredentialsException;
import com.kaonykk.financetutorapi.exception.ValidationException;
import com.kaonykk.financetutorapi.model.entity.User;
import com.kaonykk.financetutorapi.service.AuthService;
import com.kaonykk.financetutorapi.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/login")
public class ApiLoginServlet extends HttpServlet {

	private final AuthService authService = new AuthService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		try {
			LoginRequestDTO body = JsonUtil.mapper().readValue(req.getReader(), LoginRequestDTO.class);

			User user = authService.login(body.getEmail(), body.getPassword());

			HttpSession session = req.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userRole", user.getRole().name());

			resp.setStatus(200);
			JsonUtil.mapper().writeValue(resp.getWriter(), new LoginResponseDTO(user));

		} catch (ValidationException e) {
			resp.setStatus(400);
			JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", e.getMessage()));
		} catch (InvalidCredentialsException e) {
			resp.setStatus(401);
			JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error", e.getMessage()));
		}
	}
}
