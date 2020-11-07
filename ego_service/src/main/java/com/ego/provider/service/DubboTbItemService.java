package com.ego.provider.service;


import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

public interface DubboTbItemService {

	/**
	 * 商品分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid show(int page, int rows);
	
	/**
	 * 根据id修改商品状态
	 * @param id
	 * @return
	 */
	int updItemStatus(TbItem tbItem);
	
	/**
	 * 新增商品
	 * @param TbItem
	 * @param TbItemDesc
	 * @return
	 * @throws Exception 
	 */
	int insItem(TbItem item, TbItemDesc desc, TbItemParamItem paramItem) throws Exception;
	
	
	/**
	 * 根据status码查找所有itemjilu
	 * @param status
	 * @return
	 */
	List<TbItem> selByStatus(byte status);
	
	/**
	 * 根据id查询商品
	 * @param id
	 * @return
	 */
	TbItem selById(long id);
}
