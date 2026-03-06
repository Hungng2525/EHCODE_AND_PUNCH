package com.lophocvuinhon.controller.giaovien;

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

@WebServlet(urlPatterns = {"/teacher/update"})
public class UpdateController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// Khi bấm nút Sửa -> Chạy vào đây để mở Form
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Kiểm tra quyền
		HttpSession session = request.getSession();
		UserModel currentUser = (UserModel) session.getAttribute("USERMODEL");
		if (currentUser == null || !"TEACHER".equals(currentUser.getRoleCode())) {
			response.sendRedirect(request.getContextPath() + "/login?message=not_permission");
			return;
		}

		// Lấy ID người cần sửa, lôi từ Database lên
		String idStr = request.getParameter("id");
		if (idStr != null) {
			Long idToEdit = Long.parseLong(idStr);
			iUserDAO userDAO = new UserDAO();
			UserModel userEdit = userDAO.findById(idToEdit);
			
			// Ném dữ liệu sang giao diện
			request.setAttribute("USER_EDIT", userEdit);
			RequestDispatcher rd = request.getRequestDispatcher("/views/teacher/update.jsp");
			rd.forward(request, response);
		}
	}

	// Khi bấm Lưu trên Form -> Chạy vào đây để Update
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // Chống lỗi font
		
		// Lấy dữ liệu từ Form
		Long id = Long.parseLong(request.getParameter("id"));
		String fullName = request.getParameter("fullName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		
		// Gói vào Model
		UserModel user = new UserModel();
		user.setId(id);
		user.setFullName(fullName);
		user.setPhone(phone);
		user.setEmail(email);
		user.setUpdateAt(new Timestamp(System.currentTimeMillis()));
		
		// Gọi hàm Update
		iUserDAO userDAO = new UserDAO();
		userDAO.update(user);
		
		// Xong xuôi thì về trang Admin
		response.sendRedirect(request.getContextPath() + "/teacher-home");
	}
}
