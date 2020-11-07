package com.ego.sso.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;
import com.ego.sso.service.TbUserService;

@Controller
public class TbUserController {
	
	@Resource
	private TbUserService tbUserService;
	
	/**
	 * 跳转登录页面
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("user/showLogin")
	public String showLogin(@RequestHeader("Referer") String url, Model model, String interurl) {
		System.out.println("进入showLogin控制器!!!!!!!!");
		
		if (interurl!=null && !interurl.equals("")) {
			model.addAttribute("redirect", interurl);
		} else if (url!=null && !url.equals("")) {
			model.addAttribute("redirect", url);
		}
		//model.addAttribute("redirect", url);
		
		return "redirect:/login";
	}

	/** 	 
	 * SSO核心功能
	 * 登录验证
	 * @param tbUser
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("user/login")
	@ResponseBody
	public EgoResult login(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入login控制器!!!!!!!!");
		
		
		return tbUserService.selByUser(tbUser, request, response);
	}	
	
	/**
	 * 根据token查询用户信息
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping("user/token/{token}")
	@ResponseBody
	public Object selByToken(@PathVariable("token") String token, String callback) {
		
		System.out.println("进入selByToken控制器!!!!!!!!");
		System.out.println(token);
		
		if (callback!=null && !callback.equals("")) {
			MappingJacksonValue mjv = new MappingJacksonValue(tbUserService.selUserInfoByToken(token));
			
			//使用 mjv将 构造后对象 封装成jackson 字符串并作为jsonp最终返回参数数据
			mjv.setJsonpFunction(callback);
			return mjv;
		}
		
		return tbUserService.selUserInfoByToken(token);
	}	
}
