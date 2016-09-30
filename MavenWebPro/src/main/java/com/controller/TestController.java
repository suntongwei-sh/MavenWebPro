package com.controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Student;

@RequestMapping("/test/")
@Controller
public class TestController {
	@RequestMapping("index_1.do")
	public ModelAndView index_1() {
		JSONObject jo = new JSONObject();
		ModelAndView mv = new ModelAndView();
		List<Student> list = new ArrayList<Student>();
		Student student = new Student(1, "1", 1, 1);
		list.add(student);
		jo.put("student", student);
		jo.put("list", list);
		jo.put("number", 5434);
		mv.addObject("jsonst", jo.toString());
		return mv;

	}
}
