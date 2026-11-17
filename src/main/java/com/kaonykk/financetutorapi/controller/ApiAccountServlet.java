
package com.kaonykk.financetutorapi.controller;

import com.kaonykk.financetutorapi.dto.AccountResponseDTO;
import com.kaonykk.financetutorapi.model.entity.Account;
import com.kaonykk.financetutorapi.service.AccountService;
import com.kaonykk.financetutorapi.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/account")
public class ApiAccountServlet extends HttpServlet {
	private final AccountService accountService = new AccountService();

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession(false);
		if(session == null || session.getAttribute("userId")== null ){
			resp.setStatus(401);
			JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error","debes iniciar session"));
			return;
		}
		
		
		Long userId = (Long) session.getAttribute("userId");
		Account account = accountService.getByUserId(userId);
		if (account == null){
			resp.setStatus(404);
			JsonUtil.mapper().writeValue(resp.getWriter(), Map.of("error","No tienes cuenta"));
			return;
		}
		
		resp.setStatus(200);
		JsonUtil.mapper().writeValue(resp.getWriter(), new AccountResponseDTO (account));
		
	}

}
