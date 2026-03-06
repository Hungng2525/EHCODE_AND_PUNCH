package com.lophocvuinhon.controller.giaovien;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lophocvuinhon.dao.iSubmissionDAO;
import com.lophocvuinhon.dao.impl.SubmissionDAO;
import com.lophocvuinhon.model.SubmissionModel;
import com.lophocvuinhon.model.UserModel;

@WebServlet(urlPatterns = {"/teacher/submissions"})
public class TeacherSubmissionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Bảo mật: Chỉ giáo viên mới được vào đây
		HttpSession session = request.getSession();
		UserModel currentUser = (UserModel) session.getAttribute("USERMODEL");
		if (currentUser == null || !"TEACHER".equals(currentUser.getRoleCode())) {
			response.sendRedirect(request.getContextPath() + "/login?message=not_permission");
			return;
		}

		iSubmissionDAO submissionDAO = new SubmissionDAO();
		List<SubmissionModel> listSubmissions = submissionDAO.findAll();
		
		request.setAttribute("SUBMISSION_LIST", listSubmissions);
		RequestDispatcher rd = request.getRequestDispatcher("/views/teacher/submissions.jsp");
		rd.forward(request, response);
	}
}