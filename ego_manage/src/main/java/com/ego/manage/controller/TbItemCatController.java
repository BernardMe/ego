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
import com.ego.manage.service.TbItemCatService;
import com.ego.manage.service.TbItemService;

@Controller
public class TbItemCatController {

	@Resource
	private TbItemCatService tbItemCatService;
	
	/**
	 * 根据父菜单id显示所有子菜单
	 * @param id
	 * @return
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITree> show(@RequestParam(required=true, defaultValue="0") long id){
		
		
		return tbItemCatService.showChildren(id);
	}
	
	
}
