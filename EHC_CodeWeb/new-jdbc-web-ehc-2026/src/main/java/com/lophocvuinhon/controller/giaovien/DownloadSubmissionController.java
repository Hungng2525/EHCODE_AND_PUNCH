package com.lophocvuinhon.controller.giaovien;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

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

@WebServlet(urlPatterns = {"/teacher/download-submission"})
public class DownloadSubmissionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Kiểm tra quyền Giáo viên
		HttpSession session = request.getSession();
		UserModel currentUser = (UserModel) session.getAttribute("USERMODEL");
		if (currentUser == null || !"TEACHER".equals(currentUser.getRoleCode())) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String idStr = request.getParameter("id");
		if (idStr != null) {
			Long id = Long.parseLong(idStr);
			iSubmissionDAO submissionDAO = new SubmissionDAO();
			SubmissionModel submission = submissionDAO.findById(id);
			
			if (submission != null && submission.getFilePath() != null) {
				File downloadFile = new File(submission.getFilePath());
				
				if (downloadFile.exists()) {
					FileInputStream inStream = new FileInputStream(downloadFile);
					response.setContentType("application/octet-stream");
					response.setContentLength((int) downloadFile.length());
					String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
					response.setHeader("Content-Disposition", headerValue);
					
					OutputStream outStream = response.getOutputStream();
					byte[] buffer = new byte[4096];
					int bytesRead = -1;
					while ((bytesRead = inStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}
					
					inStream.close();
					outStream.close();
					return;
				}
			}
		}
		response.getWriter().println("File bài làm không tồn tại trên hệ thống!");
	}
}