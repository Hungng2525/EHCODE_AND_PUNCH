package com.lophocvuinhon.controller.giaovien;

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

// Import thư viện mới của Apache
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lophocvuinhon.dao.iAssignmentDAO;
import com.lophocvuinhon.dao.impl.AssignmentDAO;
import com.lophocvuinhon.model.AssignmentModel;
import com.lophocvuinhon.model.UserModel;

@WebServlet(urlPatterns = {"/teacher/create-assignment"})
// KHÔNG CÒN @MultipartConfig NỮA
public class CreateAssignmentController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "D:\\Assignment";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/views/teacher/create-assignment.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		UserModel currentUser = (UserModel) session.getAttribute("USERMODEL");
		
		// Khởi tạo các biến để hứng dữ liệu
		String title = "";
		String description = "";
		String savePath = "";

		// Kiểm tra xem form gửi lên có chứa File không
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				// Cấu hình bộ máy phân tích request
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				
				// Đảm bảo thư mục tồn tại
				File uploadDir = new File(UPLOAD_DIR);
				if (!uploadDir.exists()) {
					uploadDir.mkdir();
				}

				// Duyệt qua từng thành phần trong Form
				for (FileItem item : multiparts) {
					if (item.isFormField()) {
						// NẾU LÀ TEXT (Title, Description)
						String fieldName = item.getFieldName();
						if (fieldName.equals("title")) {
							title = item.getString("UTF-8");
						} else if (fieldName.equals("description")) {
							description = item.getString("UTF-8");
						}
					} else {
						// NẾU LÀ FILE UPLOAD
						String fileName = new File(item.getName()).getName();
						if (fileName != null && !fileName.isEmpty()) {
							savePath = UPLOAD_DIR + File.separator + fileName;
							// Ghi file vật lý vào ổ D
							item.write(new File(savePath));
						}
					}
				}

				// LƯU THÔNG TIN VÀO DATABASE
				AssignmentModel assignment = new AssignmentModel();
				assignment.setTitle(title);
				assignment.setDescription(description);
				assignment.setFilePath(savePath);
				assignment.setCreatedById(currentUser.getId());
				assignment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
				
				iAssignmentDAO assignmentDAO = new AssignmentDAO();
				assignmentDAO.save(assignment);
				
				response.sendRedirect(request.getContextPath() + "/teacher-home?message=upload_success");

			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().println("Lỗi khi upload file: " + e.getMessage());
			}
		}
	}
}