package com.lophocvuinhon.controller.hocsinh;

import java.io.IOException;
import java.sql.Timestamp;

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

@WebServlet(urlPatterns = {"/student/update-profile"})
public class StudentUpdateController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Kiểm tra Session đúng Sinh viên chưa
		HttpSession session = request.getSession();
		UserModel currentUser = (UserModel) session.getAttribute("USERMODEL");
		if (currentUser == null || !"STUDENT".equals(currentUser.getRoleCode())) {
			response.sendRedirect(request.getContextPath() + "/login?message=not_permission");
			return;
		}

		// 2. Lấy thông tin mới nhất từ DB dựa vào ID của chính mình
		iUserDAO userDAO = new UserDAO();
		UserModel userInfo = userDAO.findById(currentUser.getId());
		
		request.setAttribute("USER_INFO", userInfo);
		RequestDispatcher rd = request.getRequestDispatcher("/views/student/update-profile.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		UserModel currentUser = (UserModel) session.getAttribute("USERMODEL");
		
		// 1. Chỉ lấy 2 trường được phép sửa từ form gửi lên
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		
		// 2. Gói dữ liệu để Update (Tái sử dụng hàm update cũ)
		UserModel userToUpdate = new UserModel();
		userToUpdate.setId(currentUser.getId());
		userToUpdate.setFullName(currentUser.getFullName()); // Giữ nguyên họ tên cũ
		userToUpdate.setPhone(phone);
		userToUpdate.setEmail(email);
		userToUpdate.setUpdateAt(new Timestamp(System.currentTimeMillis()));
		
		// 3. Cập nhật xuống Database
		iUserDAO userDAO = new UserDAO();
		userDAO.update(userToUpdate);
		
		// 4. CỰC KỲ QUAN TRỌNG: Cập nhật lại Session để hệ thống nhớ thông tin mới
		currentUser.setPhone(phone);
		currentUser.setEmail(email);
		session.setAttribute("USERMODEL", currentUser);
		
		// Về trang chủ
		response.sendRedirect(request.getContextPath() + "/trang-chu");
	}
}