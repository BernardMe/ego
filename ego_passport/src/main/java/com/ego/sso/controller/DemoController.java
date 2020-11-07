package com.ego.sso.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {
	
	@RequestMapping("test/demo")
	public String demo(HttpServletResponse response, Model model) {
		System.out.println("进入 demo 控制器!!!!!!!!");
		
		Cookie c = new Cookie("key", "value");
		
		//System.out.println(c.toString());
		
		//1. 默认实现和HttpSession相同.
		//设置cookie存活时间,单位秒
		c.setMaxAge(10);

		//2. 默认存储路径为path=/
		//设置哪个目录下资源能访问
		c.setPath("/");
		//3. 域名和当前项目的域名相同
		c.setDomain(".bjsxt.com");
		
		response.addCookie(c);
		 
		return "redirect:/index";
	}

}
