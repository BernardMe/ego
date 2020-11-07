package com.ego.provider.service;


import java.util.List;

import com.ego.pojo.TbItemCat;

public interface DubboTbItemCatService {


	/**
	 * 根据父菜单id显示所有子菜单
	 * @param id
	 * @return
	 */
	List<TbItemCat> showChildren(long pid);
	
	/**
	 * 根据id查ItemCat对象
	 * @param id
	 * @return
	 */
	TbItemCat selByCid(long cid);
	
}
