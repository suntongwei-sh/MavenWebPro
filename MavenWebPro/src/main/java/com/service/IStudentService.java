package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entity.Student;

@Service
public interface IStudentService {
	int deleteByPrimaryKey(Integer id);

	int insert(Student record);

	int insertSelective(Student record);

	Student selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Student record);

	int updateByPrimaryKey(Student record);

	List<Student> selectStudentList();
}
