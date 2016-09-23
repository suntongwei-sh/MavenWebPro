package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.Student;

@Repository
public interface StudentDao {
	int deleteByPrimaryKey(Integer id);

	int insert(Student record);

	int insertSelective(Student record);

	Student selectByPrimaryKey(Integer id);

	List<Student> selectStudentList();

	int updateByPrimaryKeySelective(Student record);

	int updateByPrimaryKey(Student record);
}
