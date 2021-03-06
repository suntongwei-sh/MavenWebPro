package com.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Response;
import com.entity.Student;

@RequestMapping("/test/")
@Controller
public class TestController {
	@RequestMapping(value = "index_1.do", method = RequestMethod.GET)
	public ModelAndView index_1() {
		Response response = new Response();
		response.success("gdfgfgd");
		JSONObject jo = new JSONObject();
		ModelAndView mv = new ModelAndView();
		List<Student> list = new ArrayList<Student>();
		Student student = new Student(1, "1", 1, 1);
		list.add(student);
		jo.put("student", student);
		jo.put("list", list);
		jo.put("number", 5434);
		jo.put("response", response);
		mv.addObject("jsonst", jo.toString());
		return mv;

	}

	// 获取url的所有参数
	@RequestMapping("parameter.do")
	public void outparameter(HttpServletRequest request) {
		Enumeration<String> names = request.getParameterNames();
		Map<String, Object> map = new HashMap<String, Object>();
		while (names.hasMoreElements()) {
			String string = (String) names.nextElement();
			map.put(string, request.getParameter(string));

		}
		System.out.println(map.get("c"));
		System.out.println(map.toString());
	}
}
