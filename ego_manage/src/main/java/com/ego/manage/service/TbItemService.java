package com.ego.manage.service;


import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.github.pagehelper.PageInfo;

public interface TbItemService {

	/**
	 * 商品分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid show(int page, int rows);
	
	/**
	 * 批量根据ids修改商品状态
	 * @param ids
	 * @param status
	 * @return
	 */
	int update(String ids, byte status);
	
	/**
	 * 新增商品
	 * @param item
	 * @param desc
	 * @return
	 * @throws Exception 
	 */
	int insItem(TbItem item, String desc, String itemParams) throws Exception;
}
