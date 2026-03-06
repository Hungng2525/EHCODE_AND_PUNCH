package com.lophocvuinhon.controller.hocsinh;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lophocvuinhon.dao.iSubmissionDAO;
import com.lophocvuinhon.dao.impl.SubmissionDAO;
import com.lophocvuinhon.model.SubmissionModel;
import com.lophocvuinhon.model.UserModel;

@WebServlet(urlPatterns = {"/student/submit"})
public class SubmitAssignmentController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String SUBMISSION_DIR = "D:\\Submission";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Mở trang form nộp bài
		RequestDispatcher rd = request.getRequestDispatcher("/views/student/submit-assignment.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		UserModel currentUser = (UserModel) session.getAttribute("USERMODEL");
		
		String assignmentIdStr = "";
		String savePath = "";

		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				
				File uploadDir = new File(SUBMISSION_DIR);
				if (!uploadDir.exists()) uploadDir.mkdir();

				for (FileItem item : multiparts) {
					if (item.isFormField()) {
						if (item.getFieldName().equals("assignmentId")) {
							assignmentIdStr = item.getString("UTF-8");
						}
					} else {
						// Tạo tên file an toàn: Nối ID sinh viên vào trước tên file để tránh trùng lặp
						String originalFileName = new File(item.getName()).getName();
						if (originalFileName != null && !originalFileName.isEmpty()) {
							String newFileName = "student_" + currentUser.getId() + "_" + originalFileName;
							savePath = SUBMISSION_DIR + File.separator + newFileName;
							item.write(new File(savePath));
						}
					}
				}

				// Lưu vào bảng submissions
				SubmissionModel submission = new SubmissionModel();
				submission.setAssignmentId(Long.parseLong(assignmentIdStr));
				submission.setStudentId(currentUser.getId());
				submission.setFilePath(savePath);
				submission.setSubmittedAt(new Timestamp(System.currentTimeMillis()));
				
				iSubmissionDAO submissionDAO = new SubmissionDAO();
				submissionDAO.save(submission);
				
				// Quay về danh sách bài tập báo thành công
				response.sendRedirect(request.getContextPath() + "/student/assignments?message=submit_success");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}