package com.ego.provider.service;


import java.util.List;

import com.ego.pojo.TbContentCategory;

public interface DubboTbContentCatService {


	/**
	 * 根据父内容分类id显示其子内容分类
	 * @param id
	 * @return
	 */
	List<TbContentCategory> showChildren(long pid);
	
	/**
	 * 根据id查TbContentCategory对象
	 * @param id
	 * @return
	 */
	TbContentCategory selById(long id);
	
	/**
	 * 新增内容类目
	 * @param contentCat
	 * @return
	 */
	 int insContentCat(TbContentCategory contentCat);
	 
	 /**
	  * 更新ContentCat
	  * @param contentCat
	  * @return
	  */
	 int updContentCat(TbContentCategory contentCat);
	
}
