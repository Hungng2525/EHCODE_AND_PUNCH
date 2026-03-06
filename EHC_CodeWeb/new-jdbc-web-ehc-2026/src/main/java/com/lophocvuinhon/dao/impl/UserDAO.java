package com.lophocvuinhon.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lophocvuinhon.dao.iUserDAO;
import com.lophocvuinhon.model.UserModel;

public class UserDAO implements iUserDAO {

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/ehcodeandpunch2026";
			String user = "root";
			String password = "hungnguyen";
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	@Override
	public List<UserModel> findAll() {
		List<UserModel> results = new ArrayList<>();
		String sql = "SELECT * FROM users";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			// ktra có kết nối mới đi tiếp
			if (connection != null) {
				statement = connection.prepareStatement(sql);
				// nếu có điều kiện where thì phải có công đoạn init
				// parameter nên chưa dùng tới
				resultSet = statement.executeQuery();
				// dùng prepared để truyền lệnh sql vào,
				// result set để thực thi lệnh sql
				// giờ result set như 1 bảng sql, cần dùng một
				// vòng lặp để print các thông tin ra.
				while (resultSet.next()) {
					// resultSet.next() sẽ ktra nếu trong resultSet
					// có Obj != null thì mới get data lên được
					UserModel user = new UserModel();
					user.setId(resultSet.getLong("id"));
					user.setUserName(resultSet.getString("username"));
					user.setPassword(resultSet.getString("password"));
					user.setFullName(resultSet.getString("full_name"));
					user.setPhone(resultSet.getString("phone"));
					user.setEmail(resultSet.getString("email"));
					user.setStatus(resultSet.getString("status"));
					user.setCreatedAt(resultSet.getTimestamp("created_at"));
					user.setUpdateAt(resultSet.getTimestamp("updated_at"));
					results.add(user);
				}
			}
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return results;
	}

	@Override
	public UserModel findByUserName(String username) {
	    UserModel user = null;
	    
	    // SQL JOIN: Kết nối 3 bảng (users -> user_roles -> roles) để lấy code của Role
	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT u.*, r.code AS role_code ");
	    sql.append("FROM users u ");
	    sql.append("INNER JOIN user_roles ur ON u.id = ur.user_id ");
	    sql.append("INNER JOIN roles r ON ur.role_id = r.id ");
	    sql.append("WHERE u.username = ? AND u.status = ?");

	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = getConnection();
	        if (connection != null) {
	            statement = connection.prepareStatement(sql.toString());
	            statement.setString(1, username);
	            statement.setString(2, "active"); // Chỉ lấy user đang hoạt động
	            
	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                user = new UserModel();
	                // Map dữ liệu từ bảng users
	                user.setId(resultSet.getLong("id"));
	                user.setUserName(resultSet.getString("username"));
	                user.setPassword(resultSet.getString("password"));
	                user.setFullName(resultSet.getString("full_name"));
	                user.setPhone(resultSet.getString("phone"));
	                user.setEmail(resultSet.getString("email"));
	                user.setStatus(resultSet.getString("status"));
	                user.setCreatedAt(resultSet.getTimestamp("created_at"));
	                user.setUpdateAt(resultSet.getTimestamp("updated_at"));
	                
	                // Lấy Role Code
	                user.setRoleCode(resultSet.getString("role_code")); 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        try {
	            if (connection != null) connection.close();
	            if (statement != null) statement.close();
	            if (resultSet != null) resultSet.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return user;
	}
	

	public Long save(UserModel userModel) {
		Long id = null;
		String sql = "INSERT INTO users(username, password, full_name, phone, email, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
		Connection connection = null;	
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			if (connection != null) {
				connection.setAutoCommit(false);
				// tắt auto commit để quản lí transaction thù công
				statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, userModel.getUserName());
				statement.setString(2, userModel.getPassword());
				statement.setString(3, userModel.getFullName());
				statement.setString(4, userModel.getPhone());
				statement.setString(5, userModel.getEmail());
				statement.setTimestamp(6, userModel.getUpdateAt());
				statement.executeUpdate();
				resultSet = statement.getGeneratedKeys();
				// trả về số dòng ảnh hưởng
				if (resultSet.next()) {
					id = resultSet.getLong(1);
					// lấy giá trị ở cột đầu tiên
				}
				connection.commit();
				// lưu thay đổi

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
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
	@Override
	public void assignRole(Long userId, Long roleId) {
		String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = getConnection();
			if (connection != null) {
				// Lưu ý: Không cần RETURN_GENERATED_KEYS vì bảng này dùng khóa ngoại làm khóa chính
				statement = connection.prepareStatement(sql);
				statement.setLong(1, userId);
				statement.setLong(2, roleId);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) connection.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void deleteStudent(Long id) {
		String sql = "DELETE FROM users WHERE id = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = getConnection();
			if (connection != null) {
				statement = connection.prepareStatement(sql);
				statement.setLong(1, id);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) connection.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public UserModel findById(Long id) {
		UserModel user = null;
		String sql = "SELECT u.*, r.code AS role_code FROM users u " +
					 "INNER JOIN user_roles ur ON u.id = ur.user_id " +
					 "INNER JOIN roles r ON ur.role_id = r.id " +
					 "WHERE u.id = ?";
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
					user = new UserModel();
					user.setId(resultSet.getLong("id"));
					user.setUserName(resultSet.getString("username"));
					user.setPassword(resultSet.getString("password"));
					user.setFullName(resultSet.getString("full_name"));
					user.setPhone(resultSet.getString("phone"));
					user.setEmail(resultSet.getString("email"));
					user.setStatus(resultSet.getString("status"));
					user.setRoleCode(resultSet.getString("role_code"));
				}
			}
		} catch (SQLException e) {
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
		return user;
	}
	
	@Override
	public void update(UserModel user) {
		String sql = "UPDATE users SET full_name = ?, phone = ?, email = ?, updated_at = ? WHERE id = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			if (connection != null) {
				statement = connection.prepareStatement(sql);
				statement.setString(1, user.getFullName());
				statement.setString(2, user.getPhone());
				statement.setString(3, user.getEmail());
				statement.setTimestamp(4, user.getUpdateAt());
				statement.setLong(5, user.getId());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) connection.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
