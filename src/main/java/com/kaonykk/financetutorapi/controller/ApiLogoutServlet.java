package com.kaonykk.financetutorapi.controller;

import com.kaonykk.financetutorapi.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/logout")
public class ApiLogoutServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		resp.setStatus(200);
		JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("message", "Sesión cerrada"));
	}
}
