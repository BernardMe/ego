package com.ego.manage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemCatService;
import com.ego.manage.service.TbItemParamService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItemParam;

@Controller
public class TbItemParamController {

	@Resource
	private TbItemParamService tbItemParamService;
	
	/**
	 * 分页查询规格参数
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EasyUIDataGrid showPage(@RequestParam(required=true, defaultValue="1") int page, @RequestParam(required=true, defaultValue="10") int rows){
		
		return tbItemParamService.showPage(page, rows);
	}

	/**
	 * 批量根据id删除规格参数
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/item/param/delete")
	@ResponseBody
	public EgoResult delete(String ids) {
		return tbItemParamService.delete(ids);
	}
	
	/**
	 * 根据catid查询规格参数
	 * @param catid
	 * @return
	 */
	@RequestMapping("item/param/query/itemcatid/{catId}")
	@ResponseBody
	public EgoResult selectByCatid(@PathVariable long catId) {
		return tbItemParamService.selectByCatid(catId);
	}
	
	/**
	 * 新增规格参数
	 * @param itemParam
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("item/param/save/{catId}")
	@ResponseBody
	public EgoResult insItemParam(TbItemParam itemParam, @PathVariable long catId) {
		itemParam.setItemCatId(catId);
		return tbItemParamService.insItemParam(itemParam);
	}
}
