package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.pojo.Person;

@Controller
public class PersonController {

	/**
	 * 测试请求
	 * @param demo
	 * @return
	 */
	@RequestMapping("/demo1")
	@ResponseBody
	public MappingJacksonValue demo1(String callback){
		
		Person p = new Person();
		p.setName("Bernard");
		p.setAge(19);
		MappingJacksonValue mjv = new MappingJacksonValue(p);
		//使用 mjv将 构造后对象 封装成jackson 字符串并作为jsonp最终返回参数数据
		mjv.setJsonpFunction(callback);
		
		return mjv;
	}
	
}
