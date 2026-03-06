package com.lophocvuinhon.controller.login;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lophocvuinhon.dao.iUserDAO;
import com.lophocvuinhon.dao.impl.UserDAO;
import com.lophocvuinhon.model.UserModel;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/views/register.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// xu li dau tieng viet
		request.setCharacterEncoding("UTF-8");
		
		// lay du lieu tu input
		String usernameForm = request.getParameter("username");
		String passwordForm = request.getParameter("password");
		String fullNameForm = request.getParameter("fullName");
		String phoneForm = request.getParameter("phone");
		String emailForm = request.getParameter("email");
		
		// Khoi tao DAO
		iUserDAO userDAO = new UserDAO();
				
		// luu du lieu vao user model
		UserModel newUser = new UserModel();
		newUser.setUserName(usernameForm);
		newUser.setPassword(passwordForm);
		newUser.setFullName(fullNameForm);
		newUser.setPhone(phoneForm);
		newUser.setEmail(emailForm);
		newUser.setStatus("active");
		newUser.setUpdateAt(new Timestamp(System.currentTimeMillis())); // Lấy giờ hiện tại
		
		// luu user vao db
		Long newUserId = userDAO.save(newUser);
		
		if (newUserId != null) {
			//gan role cho user 
			userDAO.assignRole(newUserId, 2L);
			
			// dang ki thanh cong
			response.sendRedirect(request.getContextPath() + "/login?message=register_success");
		} else {
			// dang ki that bai
			request.setAttribute("message", "Đăng ký thất bại, tên đăng nhập có thể đã bị trùng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/register.jsp");
			rd.forward(request, response);
		}
	}
}