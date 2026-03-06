package com.lophocvuinhon.dao;

import java.util.List;

import com.lophocvuinhon.model.UserModel;

public interface iUserDAO {
	List<UserModel> findAll(); // tìm tất cả user
	
	UserModel findByUserName(String username); 
	// tìm user theo username (login)
	
	Long save(UserModel userModel);
	// dang nhap user mới
	
	void assignRole(Long userId, Long roleId);
	//dang ki tao user moi
	
	void deleteStudent(Long id);
	//xoa user
	
	UserModel findById(Long id); 
	// tim user theo id (giao vien mode)
	
	void update(UserModel user); 
	// update thong tin sinh vien
}
