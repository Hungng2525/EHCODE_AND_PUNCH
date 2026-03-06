package com.lophocvuinhon.controller.giaovien;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet; // Nhớ import
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lophocvuinhon.dao.iUserDAO;
import com.lophocvuinhon.dao.impl.UserDAO;
import com.lophocvuinhon.model.UserModel;

@WebServlet(urlPatterns = {"/teacher-home"})
// PHẢI CÓ extends HttpServlet BẠN NHÉ
public class HomeController extends HttpServlet { 
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Kiểm tra Session
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USERMODEL");
		
		if (userModel == null || !"TEACHER".equals(userModel.getRoleCode())) {
			response.sendRedirect(request.getContextPath() + "/login?message=not_permission");
			return;
		}

		// Lấy danh sách toàn bộ người dùng
		iUserDAO userDAO = new UserDAO();
		List<UserModel> listUsers = userDAO.findAll();
		request.setAttribute("USER_LIST", listUsers);

		// Mở giao diện
		RequestDispatcher rq = request.getRequestDispatcher("/views/teacher/home.jsp");
		rq.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}