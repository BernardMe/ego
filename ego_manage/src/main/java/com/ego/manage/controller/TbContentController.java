package com.ego.manage.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;

@Controller
public class TbContentController {

	@Resource
	private TbContentService tbContentService;
	
	/**
	 * 内容分页查询
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGrid show(@RequestParam long categoryId, int page, int rows){
		
		return tbContentService.show(categoryId, page, rows);
	}
	

	/**
	 * 修改内容
	 * @param content
	 * @return
	 */
	@RequestMapping("/content/query/update")
	@ResponseBody
	public EgoResult update(TbContent content) {
		return tbContentService.update(content);
	}
	
	/**
	 * 新增内容
	 * @param content
	 * @return
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public EgoResult insert(TbContent content) {
		EgoResult er = new EgoResult();
		
		if (tbContentService.insert(content) > 0) {
			er.setStatus(200);
		}
		return er;
	}

}
