package com.lophocvuinhon.controller.giaovien;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lophocvuinhon.dao.iUserDAO;
import com.lophocvuinhon.dao.impl.UserDAO;
import com.lophocvuinhon.model.UserModel;

@WebServlet(urlPatterns = {"/teacher/delete-user"})
public class DeleteController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. CHỐT BẢO MẬT CỰC KỲ QUAN TRỌNG:
		// Phải kiểm tra xem người đang định xóa có phải là Giáo viên không!
		// Nếu không có đoạn này, học sinh chỉ cần gõ đúng link là tự xóa được bạn bè.
		HttpSession session = request.getSession();
		UserModel currentUser = (UserModel) session.getAttribute("USERMODEL");
		if (currentUser == null || !"TEACHER".equals(currentUser.getRoleCode())) {
			response.sendRedirect(request.getContextPath() + "/login?message=not_permission");
			return;
		}

		// 2. Lấy ID của người cần xóa từ trên thanh URL xuống
		String idStr = request.getParameter("id");
		if (idStr != null) {
			try {
				Long idToDelete = Long.parseLong(idStr);
				
				// Tránh trường hợp Giáo viên tự bấm nút xóa chính mình (dù trên UI đã ẩn nút)
				if (idToDelete.equals(currentUser.getId())) {
					response.sendRedirect(request.getContextPath() + "/teacher-home?message=cannot_delete_self");
					return;
				}
				
				// 3. Gọi DAO để XÓA
				iUserDAO userDAO = new UserDAO();
				userDAO.deleteStudent(idToDelete);
				
			} catch (NumberFormatException e) {
				System.out.println("ID không hợp lệ!");
			}
		}

		// 4. Xóa xong thì quay lại trang giao vien
		response.sendRedirect(request.getContextPath() + "/teacher-home");
	}
}