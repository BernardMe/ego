package com.ego.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	/**
	 * 前端控制器处理/请求
	 * @param page
	 * @return
	 */
	@RequestMapping("/")
	public String showIndex(){
		//TODO 需要拐个弯，带点数据
		return "redirect:showBigpic";
		
	}
	
	/**
	 * 前端控制器处理非斜线/页面请求
	 * @param page
	 * @return
	 */
	@RequestMapping("{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
	
}
