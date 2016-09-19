package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Student;

@Controller
@RequestMapping("/student.do")
public class StudentController {
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	// 返回json
	public Student test_1() {
		Student student = new Student();
		student.setAge(14);
		student.setId(12);
		student.setName("AAA");
		student.setSex(1);
		System.out.println("我是get请求");
		return student;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void test_2() {
		System.out.println("我是post请求");
	}
}
