package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.pojo.Person;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.service.PortalMenuService;

@Controller
public class PortalMenuController {

	@Resource
	private PortalMenuService portalMenuService;
	
	/**
	 * PortalMenu请求
	 * @param demo
	 * @return
	 */
	@RequestMapping("rest/itemcat/all")
	@ResponseBody
	public MappingJacksonValue showPortalMenu(String callback){
		
		PortalMenu pm = new PortalMenu();
		pm = portalMenuService.showPortalMenu();
		MappingJacksonValue mjv = new MappingJacksonValue(pm);
		//使用 mjv将 构造后对象 封装成jackson 字符串并作为jsonp最终返回参数数据e
		mjv.setJsonpFunction(callback);
		
		return mjv;
	}
}
