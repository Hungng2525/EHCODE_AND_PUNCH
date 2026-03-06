package com.lophocvuinhon.controller.hocsinh;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lophocvuinhon.dao.iAssignmentDAO;
import com.lophocvuinhon.dao.impl.AssignmentDAO;
import com.lophocvuinhon.model.AssignmentModel;

@WebServlet(urlPatterns = {"/student/assignments"})
public class StudentAssignController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Gọi DAO lấy danh sách bài tập
		iAssignmentDAO assignmentDAO = new AssignmentDAO();
		List<AssignmentModel> listAssignments = assignmentDAO.findAll();
		
		request.setAttribute("ASSIGNMENT_LIST", listAssignments);
		RequestDispatcher rd = request.getRequestDispatcher("/views/student/assignments.jsp");
		rd.forward(request, response);
	}
}