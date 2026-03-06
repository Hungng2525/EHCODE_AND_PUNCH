package com.lophocvuinhon.controller.hocsinh;

import java.io.IOException;

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

@WebServlet(urlPatterns = {"/user/details"})
public class UserDetailController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Kiểm tra đăng nhập
		HttpSession session = request.getSession();
		if (session.getAttribute("USERMODEL") == null) {
			response.sendRedirect(request.getContextPath() + "/login?message=not_login");
			return;
		}

		// 2. Lấy ID từ URL
		String idStr = request.getParameter("id");
		if (idStr != null) {
			Long id = Long.parseLong(idStr);
			
			// 3. Gọi DAO lấy thông tin lên
			iUserDAO userDAO = new UserDAO();
			UserModel userDetail = userDAO.findById(id);
			
			if (userDetail != null) {
				// Truyền dữ liệu sang JSP
				request.setAttribute("USER_DETAIL", userDetail);
				RequestDispatcher rd = request.getRequestDispatcher("/views/user-detail.jsp");
				rd.forward(request, response);
				return;
			}
		}
		
		// Trở về trang trước nếu có lỗi
		response.sendRedirect(request.getHeader("Referer"));
	}
}