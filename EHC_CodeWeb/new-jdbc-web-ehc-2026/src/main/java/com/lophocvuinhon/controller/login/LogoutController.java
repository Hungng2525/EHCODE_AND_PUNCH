package com.lophocvuinhon.controller.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Hứng thông báo (nếu có)
		String message = request.getParameter("message");
		if ("logout_success".equals(message)) {
			request.setAttribute("message", "Bạn đã đăng xuất thành công!");
		} else if ("not_permission".equals(message)) {
			request.setAttribute("message", "Bạn không có quyền truy cập trang này!");
		} else if ("not_login".equals(message)) {
			request.setAttribute("message", "Vui lòng đăng nhập trước!");
		}
		
		// Chuyển hướng sang trang giao diện JSP
		RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Đăng xuất thường chỉ cần dùng GET là đủ
		doGet(request, response);
	}
}