package com.lophocvuinhon.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lophocvuinhon.dao.iSubmissionDAO;
import com.lophocvuinhon.model.SubmissionModel;

public class SubmissionDAO implements iSubmissionDAO {

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/ehcodeandpunch2026";
			String user = "root";
			String password = "hungnguyen"; // Đổi lại đúng pass của bạn
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	@Override
	public Long save(SubmissionModel submissionModel) {
		Long id = null;
		String sql = "INSERT INTO submissions (assignment_id, student_id, file_path, submitted_at) VALUES (?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = getConnection();
			if (connection != null) {
				connection.setAutoCommit(false);
				statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				statement.setLong(1, submissionModel.getAssignmentId());
				statement.setLong(2, submissionModel.getStudentId());
				statement.setString(3, submissionModel.getFilePath());
				statement.setTimestamp(4, submissionModel.getSubmittedAt());

				statement.executeUpdate();
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					id = resultSet.getLong(1);
				}
				connection.commit();
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public List<SubmissionModel> findAll() {
		List<SubmissionModel> list = new ArrayList<>();
		// Lệnh SQL JOIN 3 bảng để lấy tên Sinh viên và Tên bài tập
		String sql = "SELECT s.*, u.full_name, a.title FROM submissions s "
				+ "INNER JOIN users u ON s.student_id = u.id " + "INNER JOIN assignments a ON s.assignment_id = a.id "
				+ "ORDER BY s.submitted_at DESC";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			if (connection != null) {
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					SubmissionModel model = new SubmissionModel();
					model.setId(resultSet.getLong("id"));
					model.setAssignmentId(resultSet.getLong("assignment_id"));
					model.setStudentId(resultSet.getLong("student_id"));
					model.setFilePath(resultSet.getString("file_path"));
					model.setSubmittedAt(resultSet.getTimestamp("submitted_at"));

					// Set thêm 2 trường vừa tạo ở Bước 1
					model.setStudentFullName(resultSet.getString("full_name"));
					model.setAssignmentTitle(resultSet.getString("title"));

					list.add(model);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public SubmissionModel findById(Long id) {
		SubmissionModel model = null;
		String sql = "SELECT * FROM submissions WHERE id = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			if (connection != null) {
				statement = connection.prepareStatement(sql);
				statement.setLong(1, id);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					model = new SubmissionModel();
					model.setId(resultSet.getLong("id"));
					model.setFilePath(resultSet.getString("file_path"));
					// Lấy filePath là đủ để download rồi
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return model;
	}
}