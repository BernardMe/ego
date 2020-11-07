package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;

@Controller
public class TbItemController {

	@Resource
	private TbItemService tbItemService;
	
	/**
	 * 分页显示商品
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("item/list")
	@ResponseBody
	public EasyUIDataGrid show(@RequestParam(required=true, defaultValue="1") int page,
			@RequestParam(required=true, defaultValue="10") int rows){
		return tbItemService.show(page, rows);
	}
	
	/**
	 * 批量根据ids删除商品
	 * @param ids
	 * @param status
	 * @return
	 */
	@RequestMapping("rest/item/delete")
	@ResponseBody
	public EgoResult delete(@RequestParam(required=true) String ids){
		
		EgoResult result = new EgoResult();
		
		int index = tbItemService.update(ids, (byte) 3);
		
		if (index == 1){
			result.setStatus(200);
			return result;
		}
		return 	result;
	}
	
	/**
	 * 批量根据ids下架商品
	 * @param ids
	 * @param status
	 * @return
	 */
	@RequestMapping("rest/item/instock")
	@ResponseBody
	public EgoResult instock(@RequestParam(required=true) String ids){
		
		EgoResult result = new EgoResult();
		
		int index = tbItemService.update(ids, (byte) 2);
		
		if (index == 1){
			result.setStatus(200);
			return result;
		}
		return 	result;
	}
	
	/**
	 * 批量根据ids上架商品
	 * @param ids
	 * @param status
	 * @return
	 */
	@RequestMapping("rest/item/reshelf")
	@ResponseBody
	public EgoResult reshelf(@RequestParam(required=true) String ids){
		
		EgoResult result = new EgoResult();
		
		int index = tbItemService.update(ids, (byte) 1);
		
		if (index == 1){
			result.setStatus(200);
			return result;
		}
		return 	result;
	}
	
	/**
	 * 新增商品
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/save")
	@ResponseBody
	public EgoResult save(TbItem item, String desc, String itemParams){
		System.out.println(itemParams);
		
		EgoResult result = new EgoResult();
		
		int index = 0;
		try {
			index = tbItemService.insItem(item, desc, itemParams);
		} catch (Exception e) {
			// 捕获到异常
			//e.printStackTrace();
			//设置错误信息
			result.setData(e.getMessage());
			return result;
		}
		if (index == 1) {
			result.setStatus(200);
			return result;
		}
		return result;
	}
}
