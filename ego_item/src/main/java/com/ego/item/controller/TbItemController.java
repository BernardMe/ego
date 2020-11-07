package com.ego.item.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.TbItemChild;
import com.ego.item.service.TbItemDescService;
import com.ego.item.service.TbItemParamItemService;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItemDesc;

@Controller
public class TbItemController {

	@Resource
	private TbItemService tbItemService;
	@Resource
	private TbItemDescService tbItemDescService;
	@Resource
	private TbItemParamItemService tbItemParamItemService;
	
	/**
	 * 根据id查询商品详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/item/{itemId}.html")
	public String showItemDetails(@PathVariable("itemId") long itemId, Model model){
		
		TbItemChild child = tbItemService.selById(itemId);
		
		model.addAttribute("item", child);
		
		return "item";
	}
	
	/**
	 * 根据id查询商品DESC
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/item/desc/{itemId}.html", produces="text/html; charset=UTF-8")
	@ResponseBody
	public String showItemDesc(@PathVariable("itemId") long itemId){
		
		String itemDesc = tbItemDescService.selById(itemId);
			
		return itemDesc;
	}
	
	/**
	 * 根据id查询商品Param
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/item/param/{itemId}.html", produces="text/html; charset=UTF-8")
	@ResponseBody
	public String showItemParam(@PathVariable("itemId") long itemId){
		
		String itemParam = tbItemParamItemService.selParamById(itemId);
			
		return itemParam;
	}
}
