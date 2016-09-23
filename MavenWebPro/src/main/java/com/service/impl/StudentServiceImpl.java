package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.StudentDao;
import com.entity.Student;

@Service("studentService")
public class StudentServiceImpl {
	@Autowired
	private StudentDao studentDao;

	public int deleteByPrimaryKey(Integer id) {
		return studentDao.deleteByPrimaryKey(id);
	}

	public int insert(Student record) {
		return studentDao.insert(record);
	}

	public int insertSelective(Student record) {
		return studentDao.insertSelective(record);
	}

	public Student selectByPrimaryKey(Integer id) {
		return studentDao.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(Student record) {
		return studentDao.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(Student record) {
		int i = studentDao.updateByPrimaryKey(record);
		return i;
	}

	public List<Student> selectStudentList() {
		List<Student> lists = studentDao.selectStudentList();
		return lists;
	}

}
