package com.lophocvuinhon.dao;
import java.util.List;

import com.lophocvuinhon.model.AssignmentModel;

public interface iAssignmentDAO {
	Long save(AssignmentModel assignModel);
	List<AssignmentModel> findAll();
	AssignmentModel findById(Long id);
}
