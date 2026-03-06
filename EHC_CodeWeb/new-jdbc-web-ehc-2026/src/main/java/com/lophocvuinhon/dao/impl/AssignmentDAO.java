package com.lophocvuinhon.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lophocvuinhon.dao.iAssignmentDAO;
import com.lophocvuinhon.model.AssignmentModel;

public class AssignmentDAO implements iAssignmentDAO {

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/ehcodeandpunch2026";
			String user = "root";
			String password = "hungnguyen"; // Thay pass của bạn nếu cần
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	@Override
	public Long save(AssignmentModel assignModel) {
		Long id = null;
		String sql = "INSERT INTO assignments (title, description, file_path, created_by_id, created_at) VALUES (?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			if (connection != null) {
				connection.setAutoCommit(false);
				statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, assignModel.getTitle());
				statement.setString(2, assignModel.getDescription());
				statement.setString(3, assignModel.getFilePath());
				statement.setLong(4, assignModel.getCreatedById());
				statement.setTimestamp(5, assignModel.getCreatedAt());
				
				statement.executeUpdate();
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					id = resultSet.getLong(1);
				}
				connection.commit();
			}
		} catch (SQLException e) {
			if (connection != null) {
				try { connection.rollback(); } catch (SQLException e1) { e1.printStackTrace(); }
			}
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) connection.close();
				if (statement != null) statement.close();
				if (resultSet != null) resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
	@Override
	public List<AssignmentModel> findAll() {
		List<AssignmentModel> list = new ArrayList<>();
		String sql = "SELECT * FROM assignments ORDER BY created_at DESC"; // Xếp bài mới nhất lên đầu
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			if (connection != null) {
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					AssignmentModel model = new AssignmentModel();
					model.setId(resultSet.getLong("id"));
					model.setTitle(resultSet.getString("title"));
					model.setDescription(resultSet.getString("description"));
					model.setFilePath(resultSet.getString("file_path"));
					model.setCreatedById(resultSet.getLong("created_by_id"));
					model.setCreatedAt(resultSet.getTimestamp("created_at"));
					list.add(model);
				}
			}
		} catch (SQLException e) { e.printStackTrace(); } 
		finally { /* Tự thêm try-catch close connection như các hàm trước nhé */ }
		return list;
	}

	@Override
	public AssignmentModel findById(Long id) {
		AssignmentModel model = null;
		String sql = "SELECT * FROM assignments WHERE id = ?";
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
					model = new AssignmentModel();
					model.setId(resultSet.getLong("id"));
					model.setTitle(resultSet.getString("title"));
					model.setDescription(resultSet.getString("description"));
					model.setFilePath(resultSet.getString("file_path"));
					model.setCreatedById(resultSet.getLong("created_by_id"));
					model.setCreatedAt(resultSet.getTimestamp("created_at"));
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		finally { 
			try {
				if(connection != null) connection.close();
				if(statement != null) statement.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return model;
	}
}