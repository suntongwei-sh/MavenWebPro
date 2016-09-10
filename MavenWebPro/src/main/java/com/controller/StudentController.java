package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/student.do")
public class StudentController {
	@RequestMapping(method = RequestMethod.GET)
	public void test_1() {
		System.out.println("我是get请求");
	}

	@RequestMapping(method = RequestMethod.POST)
	public void test_2() {
		System.out.println("我是post请求");
	}
}
