package com.ego.portal.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.remoting.exchange.Request;
import com.ego.portal.service.TbContentService;

@Controller
public class TbContentController {

	@Resource
	private TbContentService tbContentService;
	/**
	 * 带点bigpic数据
	 * @param page
	 * @return
	 */
	@RequestMapping("/showBigpic")
	public String showBigpic(Model model){
		//从这里带点数据，再到index页面 查6个大广告
		String ads = tbContentService.showAd(6, true);
		model.addAttribute("ad1", ads);
		
		return "index";
		
		
	}
	
	
}
