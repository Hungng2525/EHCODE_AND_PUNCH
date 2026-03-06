package com.lophocvuinhon.controller.hocsinh;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lophocvuinhon.dao.iAssignmentDAO;
import com.lophocvuinhon.dao.impl.AssignmentDAO;
import com.lophocvuinhon.model.AssignmentModel;

@WebServlet(urlPatterns = {"/download-assignment"})
public class DownloadAssignmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		if (idStr != null) {
			Long id = Long.parseLong(idStr);
			iAssignmentDAO assignmentDAO = new AssignmentDAO();
			AssignmentModel assignment = assignmentDAO.findById(id);
			
			if (assignment != null && assignment.getFilePath() != null) {
				File downloadFile = new File(assignment.getFilePath());
				
				if (downloadFile.exists()) {
					FileInputStream inStream = new FileInputStream(downloadFile);
					
					// Thiết lập Header để trình duyệt hiểu đây là file cần tải về
					response.setContentType("application/octet-stream");
					response.setContentLength((int) downloadFile.length());
					String headerKey = "Content-Disposition";
					String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
					response.setHeader(headerKey, headerValue);
					
					// Bơm luồng dữ liệu (byte) từ ổ D sang trình duyệt
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
		// Nếu lỗi hoặc file không tồn tại
		response.getWriter().println("File không tồn tại trên hệ thống!");
	}
}