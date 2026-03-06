package com.lophocvuinhon.controller.hocsinh;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lophocvuinhon.dao.iUserDAO;
import com.lophocvuinhon.dao.impl.UserDAO;
import com.lophocvuinhon.model.UserModel;

@WebServlet(urlPatterns = {"/trang-chu"})
public class HomeController extends HttpServlet{
	
	private static final long serialVersionUID = -1958514720127667975L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// kiem tra session
		HttpSession session = request.getSession();
		
		// lay du lieu ma loginCOntroller da dua
		UserModel userModel = (UserModel) session.getAttribute("USERMODEL");
		
		//neu null -> chua login -> send redirect
		if(userModel == null) {
			response.sendRedirect(request.getContextPath() + "/login?message=not_login");
			return;
		}
		
		//list users
		iUserDAO userDAO = new UserDAO();
		List<UserModel> listUsers = userDAO.findAll();
		
		//gui sang jsp
		request.setAttribute("USERS_LIST", listUsers);
		
		//login thanh cong
		request.setAttribute("welcomeMsg", "Xin chao sinh vien: " + userModel.getFullName());
		
		//forward sang home view
		RequestDispatcher rq = request.getRequestDispatcher("/views/student/home.jsp");
		rq.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
