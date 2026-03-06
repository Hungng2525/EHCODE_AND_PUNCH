package com.lophocvuinhon.dao;

import java.util.List;

import com.lophocvuinhon.model.SubmissionModel;

public interface iSubmissionDAO {
	Long save(SubmissionModel submissionModel);
	List<SubmissionModel> findAll();
	SubmissionModel findById(Long id);
}
