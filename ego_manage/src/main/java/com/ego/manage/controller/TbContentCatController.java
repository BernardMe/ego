package com.ego.manage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCatService;
import com.ego.manage.service.TbItemCatService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbContentCategory;

@Controller
public class TbContentCatController {

	@Resource
	private TbContentCatService tbContentCatService;
	
	/**
	 * 根据父内容分类id显示其子内容分类
	 * @param id
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITree> show(@RequestParam(required=true, defaultValue="0") long id){
		
		return tbContentCatService.showChildren(id);
	}
	
	/**
	 * 新增内容类目
	 * @param contentCat
	 * @return
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public EgoResult create(TbContentCategory contentCat) {
		
		return tbContentCatService.create(contentCat);
	}

	/**
	 * 修改内容类目
	 * @param contentCat
	 * @return
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public EgoResult update(TbContentCategory contentCat) {
		return tbContentCatService.update(contentCat);
	}

	/**
	 * 删除内容类目
	 * @param contentCat
	 * @return
	 */
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public EgoResult delte(TbContentCategory contentCat) {
		return tbContentCatService.delete(contentCat);
	}
}
