package com.ego.provider.service;


import com.ego.pojo.TbItemParamItem;

public interface DubboTbItemParamItemService {


	/**
	 * 根据itemId查询规格参数
	 * @param catid
	 * @return
	 */
	String showItemParam(long itemId);
	

}
