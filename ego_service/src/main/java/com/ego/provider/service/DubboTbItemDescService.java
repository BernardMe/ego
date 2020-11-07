package com.ego.provider.service;



import com.ego.pojo.TbItemDesc;

public interface DubboTbItemDescService {


	/**
	 * 根据id查ItemCat对象
	 * @param id
	 * @return
	 */
	TbItemDesc selById(long id);
	
}
